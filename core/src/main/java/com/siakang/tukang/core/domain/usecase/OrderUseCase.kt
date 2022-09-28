package com.siakang.tukang.core.domain.usecase

import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Order
import com.siakang.tukang.core.domain.model.Tukang
import kotlinx.coroutines.flow.Flow

interface OrderUseCase {
    suspend fun getOfferOrder(skill: List<String>): Flow<Resource<List<Order>>>
    suspend fun getActiveOrder(tukangId: String): Flow<Resource<List<Order>>>
    suspend fun getFinishOrder(tukangId: String): Flow<Resource<List<Order>>>
    suspend fun getOrderDetail(id: String): Flow<Resource<Order>>
    suspend fun acceptOrder(orderId: String, tukang: Tukang): Flow<Resource<Unit>>
    suspend fun rejectOrder(orderId: String, tukang: Tukang): Flow<Resource<Unit>>
    suspend fun completeOrder(orderId: String): Flow<Resource<Unit>>
}