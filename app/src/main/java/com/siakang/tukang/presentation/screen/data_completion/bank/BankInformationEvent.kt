package com.siakang.tukang.presentation.screen.data_completion.bank

sealed class BankInformationEvent {
    class BankCode(val input: String): BankInformationEvent()
    class BankNumber(val input: String): BankInformationEvent()
    class OwnerName(val input: String): BankInformationEvent()
}