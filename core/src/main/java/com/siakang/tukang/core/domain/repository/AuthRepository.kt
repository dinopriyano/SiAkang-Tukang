package com.siakang.tukang.core.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthRepository {
    suspend fun loginWithEmailAndPassword(email: String, password: String): Task<AuthResult>
    suspend fun createAccountEmailAndPassword(email: String, password: String): Task<AuthResult>
    suspend fun loginWithGoogle(idToken: String): Task<AuthResult>
}