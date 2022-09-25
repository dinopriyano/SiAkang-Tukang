package com.siakang.tukang.presentation.screen.data_completion.personal_information

sealed class PersonalInformationInputEvent {
    class Name(val input: String): PersonalInformationInputEvent()
    class Phone(val input: String): PersonalInformationInputEvent()
    class EmergencyPhone(val input: String): PersonalInformationInputEvent()
}
