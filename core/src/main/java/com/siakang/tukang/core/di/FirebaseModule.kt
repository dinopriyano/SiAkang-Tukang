package com.siakang.tukang.core.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.siakang.tukang.core.di.qualifier.CategoryReference
import com.siakang.tukang.core.di.qualifier.ProductReference
import com.siakang.tukang.core.di.qualifier.UserReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun bindFirebaseAuth() = Firebase.auth

    @Singleton
    @Provides
    fun bindFirebaseStorage() = Firebase.storage

    @Singleton
    @Provides
    fun bindFirebaseStorageReference(firebaseStorage: FirebaseStorage) = firebaseStorage.reference

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore.apply {
            firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build()
        }
    }

    @Singleton
    @Provides
    @UserReference
    fun provideUsersReference(
        db: FirebaseFirestore
    ) = db.collection("tukang")

    @Singleton
    @Provides
    @CategoryReference
    fun provideCategoryReference(
        db: FirebaseFirestore
    ) = db.collection("category")

    @Singleton
    @Provides
    @ProductReference
    fun provideProductReference(
        db: FirebaseFirestore
    ) = db.collection("product")

}