package com.siakang.tukang.core.domain.repository

import com.google.firebase.firestore.Query

interface ProductRepository {
    suspend fun getAllProduct(limit: Int? = null): Query
}