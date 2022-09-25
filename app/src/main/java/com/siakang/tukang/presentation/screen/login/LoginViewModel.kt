package com.siakang.tukang.presentation.screen.login

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.siakang.tukang.domain.usecase.InputWrapper
import com.siakang.tukang.utils.validator.InputValidator
import com.siakang.tukang.core.domain.usecase.AuthUseCase
import com.siakang.tukang.core.domain.usecase.DataStoreUseCase
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Tukang
import com.siakang.tukang.core.domain.usecase.UserUseCase
import com.siakang.tukang.core.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val userUseCase: UserUseCase,
    private val dataStoreUseCase: DataStoreUseCase
): BaseViewModel() {

    var email = MutableStateFlow(InputWrapper())
        private set
    var password = MutableStateFlow(InputWrapper())
        private set

    val areInputsValid = combine(email, password) { email, pass ->
        email.value.isNotEmpty() && email.error == null && pass.value.isNotEmpty() && pass.error == null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    private val inputEvents = Channel<LoginInputEvent>(Channel.CONFLATED)

    private val _loginResponse = Channel<Resource<FirebaseUser?>>()
    val loginResponse: Flow<Resource<FirebaseUser?>> = _loginResponse.receiveAsFlow()
    private val _userResponse = Channel<Resource<Tukang?>>()
    val userResponse: Flow<Resource<Tukang?>> = _userResponse.receiveAsFlow()

    init {
        observeUserInputEvents()
    }

    private fun observeUserInputEvents() {
        viewModelScope.launch(Dispatchers.Default) {
            inputEvents.receiveAsFlow()
                .onEach { event ->
                    when (event) {
                        is LoginInputEvent.Email -> {
                            when (InputValidator.getEmailErrorIdOrNull(event.input)) {
                                null -> {
                                    email.value = email.value.copy(value = event.input, error = null)
                                }
                                else -> {
                                    email.value = email.value.copy(value = event.input)
                                }
                            }
                        }
                        is LoginInputEvent.Password -> {
                            when (InputValidator.getPasswordErrorIdOrNull(event.input)) {
                                null -> {
                                    password.value = password.value.copy(
                                        value = event.input,
                                        error = null
                                    )
                                }
                                else -> {
                                    password.value =
                                        password.value.copy(value = event.input)
                                }
                            }
                        }
                    }
                }
                .debounce(350)
                .collect { event ->
                    when (event) {
                        is LoginInputEvent.Email -> {
                            val error = InputValidator.getEmailErrorIdOrNull(event.input)
                            email.value = email.value.copy(error = error)
                        }
                        is LoginInputEvent.Password -> {
                            val error = InputValidator.getPasswordErrorIdOrNull(event.input)
                            password.value =
                                password.value.copy(error = error)
                        }
                    }
                }
        }
    }

    fun storeUid(uid: String) {
        viewModelScope.launch {
            dataStoreUseCase.storeUid(userId = uid)
        }
    }

    fun onEmailEntered(input: String) {
        inputEvents.trySend(LoginInputEvent.Email(input))
    }

    fun onPasswordEntered(input: String) {
        inputEvents.trySend(LoginInputEvent.Password(input))
    }

    fun loginWithEmailPassword() {
        viewModelScope.launch {
            authUseCase.loginWithEmailAndPassword(email.value.value, password.value.value).runFlow(_loginResponse)
        }
    }

    fun getUser(uid: String) {
        viewModelScope.launch {
            userUseCase.getUser(uid).runFlow(_userResponse)
        }
    }

}