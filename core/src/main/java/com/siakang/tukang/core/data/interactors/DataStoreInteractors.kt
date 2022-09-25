package com.siakang.tukang.core.data.interactors

import com.siakang.tukang.core.domain.usecase.DataStoreUseCase
import com.siakang.tukang.core.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class DataStoreInteractors(
    private val dataStoreRepository: DataStoreRepository
): DataStoreUseCase {
    override suspend fun getUid(): Flow<String> {
        return dataStoreRepository.getUid()
    }

    override suspend fun storeUid(userId: String) {
        dataStoreRepository.storeUid(userId)
    }

    override suspend fun clear() {
        dataStoreRepository.clear()
    }
}