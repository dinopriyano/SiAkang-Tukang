package com.siakang.tukang.core.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.siakang.tukang.core.domain.repository.CustomerRepository

class CustomerRepositoryImpl(
    private val ref: CollectionReference
): CustomerRepository {
    override suspend fun getUser(userId: String): Task<DocumentSnapshot> {
        return ref.document(userId).get()
    }
}