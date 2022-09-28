package com.siakang.tukang.core.domain.model

data class User (
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val photo_profile: String? = null,
    val phone: String = "",
    val role: String = "",
    val status: Boolean = false
)