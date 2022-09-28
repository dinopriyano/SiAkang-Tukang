package com.siakang.tukang.core.data.interactors

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Order
import com.siakang.tukang.core.domain.model.Tukang
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

    override suspend fun getFinishOrder(tukangId: String): Flow<Resource<List<Order>>> {
        return callbackFlow {
            val snapshotListener = repo.getFinishOrder(tukangId).addSnapshotListener { value, error ->
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

    override suspend fun getOrderDetail(id: String): Flow<Resource<Order>> {
        return callbackFlow {
            repo.getOrderDetail(id).addOnCompleteListener { task: Task<DocumentSnapshot> ->
                trySend(
                    if(task.isSuccessful) {
                        val order = task.result.toObject(Order::class.java)
                        if(order != null) {
                            Resource.Success(order)
                        }
                        else {
                            Resource.Failure("Ups, terjadi kesalahan!")
                        }
                    }
                    else {
                        Resource.Failure(task.exception?.localizedMessage ?: "Ups, something error!")
                    }
                )
            }
            awaitClose()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun acceptOrder(orderId: String, tukang: Tukang): Flow<Resource<Unit>> {
        return callbackFlow {
            repo.acceptOrder(orderId, tukang).addOnCompleteListener { task ->
                trySend(
                    if(task.isSuccessful) {
                        Resource.Success(Unit)
                    }
                    else {
                        Resource.Failure(task.exception?.localizedMessage ?: "Ups, something error!")
                    }
                )
            }
            awaitClose()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun rejectOrder(orderId: String, tukang: Tukang): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun completeOrder(orderId: String): Flow<Resource<Unit>> {
        return callbackFlow {
            repo.completeOrder(orderId).addOnCompleteListener { task ->
                trySend(
                    if(task.isSuccessful) {
                        Resource.Success(Unit)
                    }
                    else {
                        Resource.Failure(task.exception?.localizedMessage ?: "Ups, something error!")
                    }
                )
            }
            awaitClose()
        }.flowOn(Dispatchers.IO)
    }
}