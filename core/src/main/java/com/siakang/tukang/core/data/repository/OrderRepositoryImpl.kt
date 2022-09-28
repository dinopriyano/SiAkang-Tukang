package com.siakang.tukang.core.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import com.siakang.tukang.core.domain.model.Tukang
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

    override suspend fun getOrderDetail(id: String): Task<DocumentSnapshot> {
        return ref.document(id).get()
    }

    override suspend fun acceptOrder(orderId: String, tukang: Tukang): Task<Void> {
        return ref.document(orderId).update(mapOf<String, Any>(
            "tukangId" to tukang.id,
            "tukangName" to tukang.name
        ))
    }

    override suspend fun rejectOrder(orderId: String, tukang: Tukang): Task<Void> {
        TODO("Not yet implemented")
    }

    override suspend fun completeOrder(orderId: String): Task<Void> {
        return ref.document(orderId).update(mapOf<String, Any>(
            "complete" to true
        ))
    }
}