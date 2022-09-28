package com.siakang.tukang.core.domain.usecase

import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Chat
import com.siakang.tukang.core.domain.model.Tukang
import com.siakang.tukang.core.domain.model.User
import com.siakang.tukang.core.domain.model.WhoChat
import kotlinx.coroutines.flow.Flow

interface ChatUseCase {
    suspend fun getWhoChat(userId: String): Flow<Resource<List<WhoChat>>>
    suspend fun getChat(friendId: String, userId: String): Flow<Resource<List<Chat>>>
    suspend fun sendChat(tukang: Tukang, user: User, message: String): Flow<Resource<Unit>>
}