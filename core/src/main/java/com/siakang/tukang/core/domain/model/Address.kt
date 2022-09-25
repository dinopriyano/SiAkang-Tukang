package com.siakang.tukang.core.domain.model

data class Address(
    val id: String,
    val address: String,
    val lat: Double,
    val long: Double,
    val owner_name: String,
    val phone: String,
    val title: String,
    val user_id: String
)
