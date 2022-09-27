package com.siakang.tukang.core.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.StorageReference
import com.siakang.tukang.core.data.repository.*
import com.siakang.tukang.core.data.source.local.AppDataStore
import com.siakang.tukang.core.di.qualifier.CategoryReference
import com.siakang.tukang.core.di.qualifier.OrderReference
import com.siakang.tukang.core.di.qualifier.ProductReference
import com.siakang.tukang.core.di.qualifier.UserReference
import com.siakang.tukang.core.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(auth)
    }

    @Singleton
    @Provides
    fun provideDataStoreRepository(appDataStore: AppDataStore): DataStoreRepository {
        return DataStoreRepositoryImpl(appDataStore)
    }

    @Singleton
    @Provides
    fun provideUsersRepository(@UserReference ref: CollectionReference, storageReference: StorageReference): UserRepository {
        return UserRepositoryImpl(ref, storageReference)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(@CategoryReference ref: CollectionReference): CategoryRepository {
        return CategoryRepositoryImpl(ref)
    }

    @Singleton
    @Provides
    fun provideProductRepository(@ProductReference ref: CollectionReference): ProductRepository {
        return ProductRepositoryImpl(ref)
    }

    @Singleton
    @Provides
    fun provideOrderRepository(@OrderReference ref: CollectionReference): OrderRepository {
        return OrderRepositoryImpl(ref)
    }

}