package com.siakang.tukang.core.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.siakang.tukang.core.data.model.Resource
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {
    suspend fun loginWithEmailAndPassword(email: String, password: String): Flow<Resource<FirebaseUser?>>
    suspend fun createAccountEmailAndPassword(email: String, password: String): Flow<Resource<FirebaseUser?>>
    suspend fun loginWithGoogle(idToken: String): Flow<Resource<Boolean>>
}