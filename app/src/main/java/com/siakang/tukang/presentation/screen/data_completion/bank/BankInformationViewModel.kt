package com.siakang.tukang.presentation.screen.data_completion.bank

import androidx.lifecycle.viewModelScope
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.BankTukang
import com.siakang.tukang.core.domain.model.PersonalInformationTukang
import com.siakang.tukang.core.domain.usecase.DataStoreUseCase
import com.siakang.tukang.core.domain.usecase.UserUseCase
import com.siakang.tukang.core.presentation.base.BaseViewModel
import com.siakang.tukang.domain.usecase.InputWrapper
import com.siakang.tukang.presentation.screen.data_completion.personal_information.PersonalInformationInputEvent
import com.siakang.tukang.utils.validator.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankInformationViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val dataStoreUseCase: DataStoreUseCase
): BaseViewModel() {

    var bankCode = MutableStateFlow(InputWrapper())
        private set
    var bankNumber = MutableStateFlow(InputWrapper())
        private set
    var ownerName = MutableStateFlow(InputWrapper())
        private set

    private val inputEvents = Channel<BankInformationEvent>(Channel.CONFLATED)

    val areInputsValid = combine(bankCode, bankNumber, ownerName) { bankCode, bankNumber, ownerName ->
        bankCode.value.isNotEmpty() && bankCode.error == null && bankNumber.value.isNotEmpty() && bankNumber.error == null && ownerName.value.isNotEmpty() && ownerName.error == null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    private val _storeResponse = Channel<Resource<BankTukang>>()
    val storeResponse: Flow<Resource<BankTukang>> = _storeResponse.receiveAsFlow()

    init {
        observeUserInputEvents()
    }

    private fun observeUserInputEvents() {
        viewModelScope.launch(Dispatchers.Default) {
            inputEvents.receiveAsFlow()
                .onEach { event ->
                    when (event) {
                        is BankInformationEvent.BankCode -> {
                            when (InputValidator.getBankCodeErrorIdOrNull(event.input)) {
                                null -> {
                                    bankCode.value = bankCode.value.copy(value = event.input, error = null)
                                }
                                else -> {
                                    bankCode.value = bankCode.value.copy(value = event.input)
                                }
                            }
                        }
                        is BankInformationEvent.BankNumber -> {
                            when (InputValidator.getBankNumberErrorIdOrNull(event.input)) {
                                null -> {
                                    bankNumber.value = bankNumber.value.copy(
                                        value = event.input,
                                        error = null
                                    )
                                }
                                else -> {
                                    bankNumber.value =
                                        bankNumber.value.copy(value = event.input)
                                }
                            }
                        }
                        is BankInformationEvent.OwnerName -> {
                            when (InputValidator.getOwnerNameErrorIdOrNull(event.input)) {
                                null -> {
                                    ownerName.value = ownerName.value.copy(
                                        value = event.input,
                                        error = null
                                    )
                                }
                                else -> {
                                    ownerName.value =
                                        ownerName.value.copy(value = event.input)
                                }
                            }
                        }
                    }
                }
                .debounce(350)
                .collect { event ->
                    when (event) {
                        is BankInformationEvent.BankCode -> {
                            val error = InputValidator.getBankCodeErrorIdOrNull(event.input)
                            bankCode.value = bankCode.value.copy(error = error)
                        }
                        is BankInformationEvent.BankNumber -> {
                            val error = InputValidator.getBankNumberErrorIdOrNull(event.input)
                            bankNumber.value =
                                bankNumber.value.copy(error = error)
                        }
                        is BankInformationEvent.OwnerName -> {
                            val error = InputValidator.getOwnerNameErrorIdOrNull(event.input)
                            ownerName.value =
                                ownerName.value.copy(error = error)
                        }
                    }
                }
        }
    }

    fun onBankCodeEntered(input: String) {
        inputEvents.trySend(BankInformationEvent.BankCode(input))
    }

    fun onBankNumberEntered(input: String) {
        inputEvents.trySend(BankInformationEvent.BankNumber(input))
    }

    fun onOwnerNameEntered(input: String) {
        inputEvents.trySend(BankInformationEvent.OwnerName(input))
    }

    fun storeBankInformation() {
        viewModelScope.launch {
            _storeResponse.send(Resource.Loading)
            userUseCase.updateBank(
                dataStoreUseCase.getUid().first(),
                BankTukang(
                    bankCode.value.value,
                    bankNumber.value.value,
                    ownerName.value.value
                )
            ).collect { result ->
                _storeResponse.trySend(result)
            }
        }
    }

}