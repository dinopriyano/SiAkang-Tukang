package com.siakang.tukang.core.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.siakang.tukang.core.domain.repository.OrderRepository

class OrderRepositoryImpl(
    private val ref: CollectionReference
): OrderRepository {
    override suspend fun getOfferOrder(skill: List<String>): Query {
        return ref.whereIn("categoryId", skill).whereEqualTo("tukangId", "").orderBy("orderDate").limit(10)
    }

    override suspend fun getActiveOrder(tukangId: String): Query {
        return ref.whereEqualTo("tukangId", tukangId).orderBy("orderDate").limit(10)
    }
}