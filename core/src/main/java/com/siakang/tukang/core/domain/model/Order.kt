package com.siakang.tukang.core.domain.model

import com.google.firebase.Timestamp

data class Order(
    val address: String = "",
    val category: String = "",
    val categoryId: String = "",
    val detail: String = "",
    val `file`: List<String> = listOf(),
    val id: String = "",
    val note: String = "",
    val orderDate: Timestamp = Timestamp.now(),
    val requestDate: Timestamp = Timestamp.now(),
    val subCategory: String = "",
    val userId: String = "",
    val userName: String = "",
    val tukangId: String = "",
    val tukangName: String = ""
)