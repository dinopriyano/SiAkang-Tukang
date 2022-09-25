package com.siakang.tukang.presentation.screen.data_completion.personal_information

import androidx.lifecycle.viewModelScope
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.PersonalInformationTukang
import com.siakang.tukang.core.domain.usecase.DataStoreUseCase
import com.siakang.tukang.core.domain.usecase.UserUseCase
import com.siakang.tukang.core.presentation.base.BaseViewModel
import com.siakang.tukang.domain.usecase.InputWrapper
import com.siakang.tukang.utils.validator.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class PersonalInformationViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : BaseViewModel() {

    var name = MutableStateFlow(InputWrapper())
        private set
    var phone = MutableStateFlow(InputWrapper())
        private set
    var emergencyPhone = MutableStateFlow(InputWrapper())
        private set

    private val inputEvents = Channel<PersonalInformationInputEvent>(Channel.CONFLATED)

    val areInputsValid = combine(name, phone, emergencyPhone) { name, phone, emergencyPhone ->
        name.value.isNotEmpty() && name.error == null && phone.value.isNotEmpty() && phone.error == null && emergencyPhone.value.isNotEmpty() && emergencyPhone.error == null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    private val _storeResponse = Channel<Resource<PersonalInformationTukang>>()
    val storeResponse: Flow<Resource<PersonalInformationTukang>> = _storeResponse.receiveAsFlow()

    init {
        observeUserInputEvents()
    }

    private fun observeUserInputEvents() {
        viewModelScope.launch(Dispatchers.Default) {
            inputEvents.receiveAsFlow()
                .onEach { event ->
                    when (event) {
                        is PersonalInformationInputEvent.Name -> {
                            when (InputValidator.getNameErrorIdOrNull(event.input)) {
                                null -> {
                                    name.value = name.value.copy(value = event.input, error = null)
                                }
                                else -> {
                                    name.value = name.value.copy(value = event.input)
                                }
                            }
                        }
                        is PersonalInformationInputEvent.Phone -> {
                            when (InputValidator.getPhoneErrorIdOrNull(event.input)) {
                                null -> {
                                    phone.value = phone.value.copy(
                                        value = event.input,
                                        error = null
                                    )
                                }
                                else -> {
                                    phone.value =
                                        phone.value.copy(value = event.input)
                                }
                            }
                        }
                        is PersonalInformationInputEvent.EmergencyPhone -> {
                            when (InputValidator.getPhoneErrorIdOrNull(event.input)) {
                                null -> {
                                    emergencyPhone.value = emergencyPhone.value.copy(
                                        value = event.input,
                                        error = null
                                    )
                                }
                                else -> {
                                    emergencyPhone.value =
                                        emergencyPhone.value.copy(value = event.input)
                                }
                            }
                        }
                    }
                }
                .debounce(350)
                .collect { event ->
                    when (event) {
                        is PersonalInformationInputEvent.Name -> {
                            val error = InputValidator.getNameErrorIdOrNull(event.input)
                            name.value = name.value.copy(error = error)
                        }
                        is PersonalInformationInputEvent.Phone -> {
                            val error = InputValidator.getPhoneErrorIdOrNull(event.input)
                            phone.value =
                                phone.value.copy(error = error)
                        }
                        is PersonalInformationInputEvent.EmergencyPhone -> {
                            val error = InputValidator.getPhoneErrorIdOrNull(event.input)
                            emergencyPhone.value =
                                emergencyPhone.value.copy(error = error)
                        }
                    }
                }
        }
    }

    fun onNameEntered(input: String) {
        inputEvents.trySend(PersonalInformationInputEvent.Name(input))
    }

    fun onPhoneEntered(input: String) {
        inputEvents.trySend(PersonalInformationInputEvent.Phone(input))
    }

    fun onEmergencyPhoneEntered(input: String) {
        inputEvents.trySend(PersonalInformationInputEvent.EmergencyPhone(input))
    }

    fun storePersonalInformation() {
        viewModelScope.launch {
            _storeResponse.send(Resource.Loading)
            userUseCase.updatePersonalInformation(
                dataStoreUseCase.getUid().first(),
                PersonalInformationTukang(
                    name.value.value,
                    phone.value.value,
                    emergencyPhone.value.value
                )
            ).collect { result ->
                _storeResponse.trySend(result)
            }
        }
    }
}