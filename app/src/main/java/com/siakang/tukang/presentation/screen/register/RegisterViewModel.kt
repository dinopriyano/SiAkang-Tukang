package com.siakang.tukang.presentation.screen.register

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.siakang.tukang.domain.usecase.InputWrapper
import com.siakang.tukang.utils.validator.InputValidator
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Tukang
import com.siakang.tukang.core.domain.usecase.AuthUseCase
import com.siakang.tukang.core.domain.usecase.UserUseCase
import com.siakang.tukang.core.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

const val EMAIL = "email"
const val PASSWORD = "password"
const val CONFIRM_PASSWORD = "confirm_password"

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val userUseCase: UserUseCase,
    private val handle: SavedStateHandle
) : BaseViewModel() {

    val email = handle.getStateFlow(EMAIL, InputWrapper())
    val password = handle.getStateFlow(PASSWORD, InputWrapper())
    val confirmPassword = handle.getStateFlow(CONFIRM_PASSWORD, InputWrapper())

    val areInputsValid = combine(email, password, confirmPassword) { email, pass, confirmPass ->
        email.value.isNotEmpty()
                && email.error == null
                && pass.value.isNotEmpty()
                && pass.error == null
                && confirmPass.value.isNotEmpty()
                && confirmPass.error == null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    private val inputEvents = Channel<RegisterInputEvent>(Channel.CONFLATED)

    private val _registerResponse = Channel<Resource<FirebaseUser?>>()
    val registerResponse: Flow<Resource<FirebaseUser?>> = _registerResponse.receiveAsFlow()
    private val _saveResponse = Channel<Resource<Tukang?>>()
    val saveResponse: Flow<Resource<Tukang?>> = _saveResponse.receiveAsFlow()

    init {
        observeUserInputEvents()
    }

    private fun observeUserInputEvents() {
        viewModelScope.launch(Dispatchers.Default) {
            inputEvents.receiveAsFlow()
                .onEach { event ->
                    when (event) {
                        is RegisterInputEvent.Email -> {
                            when (InputValidator.getEmailErrorIdOrNull(event.input)) {
                                null -> {
                                    handle[EMAIL] =
                                        email.value.copy(value = event.input, error = null)
                                }
                                else -> {
                                    handle[EMAIL] = email.value.copy(value = event.input)
                                }
                            }
                        }
                        is RegisterInputEvent.Password -> {
                            when (InputValidator.getPasswordErrorIdOrNull(event.input)) {
                                null -> {
                                    handle[PASSWORD] = password.value.copy(
                                        value = event.input,
                                        error = null
                                    )
                                }
                                else -> {
                                    handle[PASSWORD] = password.value.copy(value = event.input)
                                }
                            }
                        }
                        is RegisterInputEvent.ConfirmPassword -> {
                            when (InputValidator.getConfirmPasswordErrorIdOrNull(
                                event.input,
                                password.value.value
                            )) {
                                null -> {
                                    handle[CONFIRM_PASSWORD] = confirmPassword.value.copy(
                                        value = event.input,
                                        error = null
                                    )
                                }
                                else -> {
                                    handle[CONFIRM_PASSWORD] =
                                        confirmPassword.value.copy(value = event.input)
                                }
                            }
                        }
                    }
                }
                .debounce(350)
                .collect { event ->
                    when (event) {
                        is RegisterInputEvent.Email -> {
                            val error = InputValidator.getEmailErrorIdOrNull(event.input)
                            handle[EMAIL] = email.value.copy(error = error)
                        }
                        is RegisterInputEvent.Password -> {
                            val error = InputValidator.getPasswordErrorIdOrNull(event.input)
                            handle[PASSWORD] = password.value.copy(error = error)
                            val errorConf = InputValidator.getConfirmPasswordErrorIdOrNull(
                                confirmPassword.value.value,
                                event.input
                            )
                            handle[CONFIRM_PASSWORD] = confirmPassword.value.copy(error = errorConf)
                        }
                        is RegisterInputEvent.ConfirmPassword -> {
                            val error = InputValidator.getConfirmPasswordErrorIdOrNull(
                                event.input,
                                password.value.value
                            )
                            handle[CONFIRM_PASSWORD] = confirmPassword.value.copy(error = error)
                        }
                    }
                }
        }
    }

    fun onEmailEntered(input: String) {
        inputEvents.trySend(RegisterInputEvent.Email(input))
    }

    fun onPasswordEntered(input: String) {
        inputEvents.trySend(RegisterInputEvent.Password(input))
    }

    fun onConfirmPasswordEntered(input: String) {
        inputEvents.trySend(RegisterInputEvent.ConfirmPassword(input))
    }

    fun createAccountWithEmailPassword() {
        viewModelScope.launch {
            _registerResponse.send(Resource.Loading)
            authUseCase.createAccountEmailAndPassword(email.value.value, password.value.value)
                .collect { result ->
                    _registerResponse.send(result)
                }
        }
    }

    fun saveUser(uid: String) {
        viewModelScope.launch {
            _saveResponse.send(Resource.Loading)
            userUseCase.createUser(
                Tukang(
                    id = uid,
                    name = "",
                    email = email.value.value,
                    photoProfile = null,
                    phone = "",
                    status = false,
                    areDataComplete = false
                )
            ).collect { result ->
                Log.i("Kontol", "saveUser: $result")
                _saveResponse.send(result)
            }
        }
    }

}