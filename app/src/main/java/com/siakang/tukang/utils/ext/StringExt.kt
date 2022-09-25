package com.siakang.tukang.utils.ext

import java.util.regex.Pattern

fun String.isValidEmail(): Boolean {
    val pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
    if(pattern.matcher(this).find()){
        return true
    }

    return false
}

fun String.isValidPassword(): Boolean {
    val pattern = Pattern.compile("""^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#${'$'}%!\-_?&])(?=\S+${'$'}).{8,}""")
    if(pattern.matcher(this).matches()){
        return true
    }

    return false
}

fun String.isValidPhone(): Boolean {
    val pattern = Pattern.compile("""^(^\+62|62|^08)(\d{3,4}-?){2}\d{3,4}${'$'}""")
    if(pattern.matcher(this).matches()){
        return true
    }

    return false
}

fun String.isValidName(): Boolean {
    return this.length >= 3
}

fun String.getFirstName(): String {
    return this.split(" ")[0]
}

fun String.isValidConfirmPassword(password: String): Boolean {
    return this == password
}