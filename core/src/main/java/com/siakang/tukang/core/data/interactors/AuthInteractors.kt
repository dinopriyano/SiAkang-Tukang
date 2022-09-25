package com.siakang.tukang.core.data.interactors

import com.google.firebase.auth.FirebaseUser
import com.siakang.tukang.core.domain.repository.AuthRepository
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.usecase.AuthUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

class AuthInteractors(
    private val authRepository: AuthRepository
): AuthUseCase {
    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> {
        return callbackFlow {
            authRepository.loginWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                trySend(
                    if(task.isSuccessful) {
                        Resource.Success(task.result.user)
                    }
                    else {
                        Resource.Failure(task.exception?.localizedMessage ?: "Ups, something error!")
                    }
                )
            }
            awaitClose()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun createAccountEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> {
        return callbackFlow {
            authRepository.createAccountEmailAndPassword(email, password).addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    trySend(Resource.Success(task.result?.user))
                }
                else {
                    trySend(Resource.Failure(task.exception?.localizedMessage ?: "Ups, something error!"))
                }
            }
            awaitClose()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun loginWithGoogle(idToken: String): Flow<Resource<Boolean>> {
        TODO("Not yet implemented")
    }
}