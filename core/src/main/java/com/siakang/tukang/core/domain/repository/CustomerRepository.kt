package com.siakang.tukang.core.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

interface CustomerRepository {
    suspend fun getUser(userId: String): Task<DocumentSnapshot>
}