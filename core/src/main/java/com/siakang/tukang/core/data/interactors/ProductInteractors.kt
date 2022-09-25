package com.siakang.tukang.core.data.interactors

import com.siakang.tukang.core.domain.usecase.ProductUseCase
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Product
import com.siakang.tukang.core.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

class ProductInteractors(
    private val productRepository: ProductRepository
): ProductUseCase {
    override suspend fun getAllProduct(limit: Int?): Flow<Resource<List<Product>>> {
        return callbackFlow {
            val snapshotListener = productRepository.getAllProduct(limit).addSnapshotListener { value, error ->
                val response = if (value != null) {
                    val category = value.toObjects(Product::class.java)
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