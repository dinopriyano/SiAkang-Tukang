package com.siakang.tukang.utils.validator

import com.siakang.tukang.utils.ext.*

object InputValidator {

    fun getEmailErrorIdOrNull(input: String): String? {
        return when {
            !input.isValidEmail() -> "Format email salah"
            else -> null
        }
    }

    fun getPasswordErrorIdOrNull(input: String): String? {
        return when {
            !input.isValidPassword() -> "Password harus mengandung huruf besar, huruf kecil, simbol, dan setidaknya 8 karakter"
            else -> null
        }
    }

    fun getConfirmPasswordErrorIdOrNull(input: String, password: String): String? {
        return when {
            !input.isValidConfirmPassword(password) -> "Confirm password harus sama dengan password"
            else -> null
        }
    }

    fun getNameErrorIdOrNull(input: String): String? {
        return when {
            !input.isValidName() -> "Nama setidaknya 3 karakter"
            else -> null
        }
    }

    fun getPhoneErrorIdOrNull(input: String): String? {
        return when {
            !input.isValidPhone() -> "Format telepon tidak valid"
            else -> null
        }
    }

    fun getBankCodeErrorIdOrNull(input: String): String? {
        return when {
            !input.isValidBankCode() -> "Format kode bank tidak valid"
            else -> null
        }
    }

    fun getBankNumberErrorIdOrNull(input: String): String? {
        return when {
            !input.isValidBankNumber() -> "Format nomor rekening tidak valid"
            else -> null
        }
    }

    fun getOwnerNameErrorIdOrNull(input: String): String? {
        return when {
            !input.isValidName() -> "Nama setidaknya 3 karakter"
            else -> null
        }
    }

}