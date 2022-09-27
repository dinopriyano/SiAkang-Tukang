package com.siakang.tukang.core.domain.usecase

import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderUseCase {
    suspend fun getOfferOrder(skill: List<String>): Flow<Resource<List<Order>>>
    suspend fun getActiveOrder(tukangId: String): Flow<Resource<List<Order>>>
}