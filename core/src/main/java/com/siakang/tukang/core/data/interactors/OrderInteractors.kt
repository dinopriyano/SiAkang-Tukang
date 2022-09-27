package com.siakang.tukang.core.data.interactors

import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Order
import com.siakang.tukang.core.domain.repository.OrderRepository
import com.siakang.tukang.core.domain.usecase.OrderUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

class OrderInteractors(
    private val repo: OrderRepository
): OrderUseCase {
    override suspend fun getOfferOrder(skill: List<String>): Flow<Resource<List<Order>>> {
        return callbackFlow {
            val snapshotListener = repo.getOfferOrder(skill).addSnapshotListener { value, error ->
                val response = if (value != null) {
                    val orders = value.toObjects(Order::class.java)
                    Resource.Success(orders)
                } else {
                    Resource.Failure(error?.message ?: error.toString())
                }
                trySend(response)
            }

            awaitClose {
                snapshotListener.remove()
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getActiveOrder(tukangId: String): Flow<Resource<List<Order>>> {
        return callbackFlow {
            val snapshotListener = repo.getActiveOrder(tukangId).addSnapshotListener { value, error ->
                val response = if (value != null) {
                    val orders = value.toObjects(Order::class.java)
                    Resource.Success(orders)
                } else {
                    Resource.Failure(error?.message ?: error.toString())
                }
                trySend(response)
            }

            awaitClose {
                snapshotListener.remove()
            }
        }.flowOn(Dispatchers.IO)
    }
}