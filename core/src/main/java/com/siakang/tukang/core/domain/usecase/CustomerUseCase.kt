package com.siakang.tukang.core.domain.usecase

import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface CustomerUseCase {
    suspend fun getUser(userId: String): Flow<Resource<User?>>
}