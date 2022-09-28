package com.siakang.tukang.core.data.interactors

import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.*
import com.siakang.tukang.core.domain.repository.ChatRepository
import com.siakang.tukang.core.domain.usecase.ChatUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

class ChatInteractors(
    private val chatRepository: ChatRepository
): ChatUseCase {
    override suspend fun getWhoChat(userId: String): Flow<Resource<List<WhoChat>>> {
        return callbackFlow {
            val snapshotListener = chatRepository.getWhoChat(userId).addSnapshotListener { value, error ->
                val response = if (value != null) {
                    val orders = value.toObjects(WhoChat::class.java)
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

    override suspend fun getChat(friendId: String, userId: String): Flow<Resource<List<Chat>>> {
        return callbackFlow {
            val snapshotListener = chatRepository.getChat(friendId, userId).addSnapshotListener { value, error ->
                val response = if (value != null) {
                    val orders = value.toObjects(Chat::class.java)
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

    override suspend fun sendChat(
        tukang: Tukang,
        user: User,
        message: String
    ): Flow<Resource<Unit>> {
        return callbackFlow {
            chatRepository.sendChat(tukang, user, message).addOnCompleteListener { task ->
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