package com.siakang.tukang.core.domain.repository

import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun getOfferOrder(skill: List<String>): Query
    suspend fun getActiveOrder(tukangId: String): Query
}