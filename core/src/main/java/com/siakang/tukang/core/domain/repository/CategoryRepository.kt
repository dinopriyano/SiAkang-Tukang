package com.siakang.tukang.core.domain.repository

import com.google.firebase.firestore.Query

interface CategoryRepository {
    suspend fun getMenu(limit: Int): Query
}