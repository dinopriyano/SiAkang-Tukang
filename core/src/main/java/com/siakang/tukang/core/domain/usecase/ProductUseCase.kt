package com.siakang.tukang.core.domain.usecase

import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductUseCase {
    suspend fun getAllProduct(limit: Int? = null): Flow<Resource<List<Product>>>
}