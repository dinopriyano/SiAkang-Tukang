package com.siakang.tukang.core.domain.model

import com.google.firebase.Timestamp
import java.util.*

data class Product(
    val category: String = "",
    val desc: String = "",
    val id: String = "",
    val image_url: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val weight: Double = 0.0,
    val created_at: Timestamp = Timestamp(Date())
)
