package com.siakang.tukang.presentation.screen.login

sealed class LoginInputEvent {
    class Email(val input: String): LoginInputEvent()
    class Password(val input: String): LoginInputEvent()
}