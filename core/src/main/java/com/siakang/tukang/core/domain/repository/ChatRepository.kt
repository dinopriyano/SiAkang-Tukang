package com.siakang.tukang.core.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.siakang.tukang.core.domain.model.Tukang
import com.siakang.tukang.core.domain.model.User

interface ChatRepository {
    suspend fun getWhoChat(userId: String): Query
    suspend fun getChat(friendId: String, userId: String): Query
    suspend fun sendChat(tukang: Tukang, user: User, message: String): Task<Unit>
}