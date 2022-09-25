package com.siakang.tukang.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun getUid(): Flow<String>
    suspend fun storeUid(userId: String)
    suspend fun clear()
}