package com.siakang.tukang.core.domain.usecase

import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryUseCase {
    suspend fun getMenu(limit: Int): Flow<Resource<List<Category>>>
}