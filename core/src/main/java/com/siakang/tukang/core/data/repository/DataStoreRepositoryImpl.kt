package com.siakang.tukang.core.data.repository

import com.siakang.tukang.core.data.source.local.AppDataStore
import com.siakang.tukang.core.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class DataStoreRepositoryImpl(private val appDataStore: AppDataStore): DataStoreRepository {
    override suspend fun getUid(): Flow<String> {
        return appDataStore.Uid
    }

    override suspend fun getAreDataComplete(): Flow<Boolean> {
        return appDataStore.areDataComplete
    }

    override suspend fun storeUid(userId: String) {
        appDataStore.storeData(AppDataStore.UID, userId)
    }

    override suspend fun clear() {
        appDataStore.clear()
    }
}