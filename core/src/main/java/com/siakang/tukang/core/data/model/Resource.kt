package com.siakang.tukang.core.data.model

sealed class Resource<out T> {
    data class Success<T>(val value:T): Resource<T>()
    data class Failure(
        val message: String
    ): Resource<Nothing>()

    object Loading: Resource<Nothing>()
    object Empty: Resource<Nothing>()
}