package com.siakang.tukang.core.domain.model

data class Tukang (
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val photoProfile: String? = null,
    val phone: String = "",
    val emergencyPhone: String = "",
    val status: Boolean = false,
    val areDataComplete: Boolean = false,
    val codeBank: String = "",
    val bankNumber: String = "",
    val ownerName: String = "",
    val skills: List<String> = emptyList(),
    val idCard: String? = null,
    val skck: String? = null
)