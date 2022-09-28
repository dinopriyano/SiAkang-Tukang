package com.siakang.tukang.core.data.interactors

import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Tukang
import com.siakang.tukang.core.domain.model.User
import com.siakang.tukang.core.domain.repository.CustomerRepository
import com.siakang.tukang.core.domain.usecase.CustomerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

class CustomerInteractors(
    private val repo: CustomerRepository
): CustomerUseCase {
    override suspend fun getUser(userId: String): Flow<Resource<User?>> {
        return callbackFlow {
            repo.getUser(userId).addOnCompleteListener { task ->
                trySend(
                    if(task.isSuccessful) {
                        val user = task.result.toObject(User::class.java)
                        if(user != null) {
                            Resource.Success(user)
                        }
                        else {
                            Resource.Failure("Ups, user tidak ada!")
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
}