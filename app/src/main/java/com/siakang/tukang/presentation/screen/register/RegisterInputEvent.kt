package com.siakang.tukang.presentation.screen.register

sealed class RegisterInputEvent {
    class Email(val input: String): RegisterInputEvent()
    class Password(val input: String): RegisterInputEvent()
    class ConfirmPassword(val input: String): RegisterInputEvent()
}