package com.siakang.tukang.core.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.siakang.tukang.core.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val categoryRef: CollectionReference
): CategoryRepository {
    override suspend fun getMenu(limit: Int): Query {
        return categoryRef.limit(limit.toLong())
    }
}