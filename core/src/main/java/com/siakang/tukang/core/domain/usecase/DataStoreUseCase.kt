package com.siakang.tukang.core.domain.usecase

import kotlinx.coroutines.flow.Flow

interface DataStoreUseCase {
    suspend fun getUid(): Flow<String>
    suspend fun getAreDataComplete(): Flow<Boolean>
    suspend fun storeUid(userId: String)
    suspend fun clear()
}