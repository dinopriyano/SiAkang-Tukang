package com.siakang.tukang.utils.ext

fun Double.toCurrency(): String {
    return String.format("%,.0f", this).let {
        "Rp".plus(it)
    }
}