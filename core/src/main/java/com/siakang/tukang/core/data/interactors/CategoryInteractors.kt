package com.siakang.tukang.core.data.interactors

import com.siakang.tukang.core.domain.usecase.CategoryUseCase
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Category
import com.siakang.tukang.core.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

class CategoryInteractors(
    private val categoryRepository: CategoryRepository
): CategoryUseCase{
    override suspend fun getMenu(limit: Int): Flow<Resource<List<Category>>> {
        return callbackFlow {
            val snapshotListener = categoryRepository.getMenu(limit).addSnapshotListener { value, error ->
                val response = if (value != null) {
                    val category = value.toObjects(Category::class.java)
                    Resource.Success(category)
                } else {
                    Resource.Failure(error?.message ?: error.toString())
                }
                trySend(response)
            }

            awaitClose {
                snapshotListener.remove()
            }
        }.flowOn(Dispatchers.IO)
    }
}