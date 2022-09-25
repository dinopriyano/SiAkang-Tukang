package com.siakang.tukang.core.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.siakang.tukang.core.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val ref: CollectionReference
): ProductRepository {
    override suspend fun getAllProduct(limit: Int?): Query {
        return if(limit == null) ref.orderBy("created_at") else ref.orderBy("created_at").limit(limit.toLong())
    }
}