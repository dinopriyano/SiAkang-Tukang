package com.siakang.tukang.core.di

import com.siakang.tukang.core.data.interactors.*
import com.siakang.tukang.core.domain.repository.*
import com.siakang.tukang.core.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideAuthUseCase(repository: AuthRepository): AuthUseCase {
        return AuthInteractors(repository)
    }

    @Singleton
    @Provides
    fun provideDataStoreUseCase(repository: DataStoreRepository): DataStoreUseCase {
        return DataStoreInteractors(repository)
    }

    @Singleton
    @Provides
    fun provideUserUseCase(repository: UserRepository): UserUseCase {
        return UserInteractors(repository)
    }

    @Singleton
    @Provides
    fun provideCategoryUseCase(repository: CategoryRepository): CategoryUseCase {
        return CategoryInteractors(repository)
    }

    @Singleton
    @Provides
    fun provideProductUseCase(repository: ProductRepository): ProductUseCase {
        return ProductInteractors(repository)
    }

    @Singleton
    @Provides
    fun provideOrderUseCase(repository: OrderRepository): OrderUseCase {
        return OrderInteractors(repository)
    }

    @Singleton
    @Provides
    fun provideChatUseCase(repository: ChatRepository): ChatUseCase {
        return ChatInteractors(repository)
    }

    @Singleton
    @Provides
    fun provideCustomerUseCase(repository: CustomerRepository): CustomerUseCase {
        return CustomerInteractors(repository)
    }

}