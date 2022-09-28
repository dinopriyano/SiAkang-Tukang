package com.siakang.tukang.core.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.siakang.tukang.core.domain.model.Tukang

interface OrderRepository {
    suspend fun getOfferOrder(skill: List<String>): Query
    suspend fun getActiveOrder(tukangId: String): Query
    suspend fun getOrderDetail(id: String): Task<DocumentSnapshot>
    suspend fun acceptOrder(orderId: String, tukang: Tukang): Task<Void>
    suspend fun rejectOrder(orderId: String, tukang: Tukang): Task<Void>
    suspend fun completeOrder(orderId: String): Task<Void>
}