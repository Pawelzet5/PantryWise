package com.example.pantrywise.di

import com.example.pantrywise.data.repository.IProductRepository
import com.example.pantrywise.data.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindProductRepository(
        impl: ProductRepository
    ): IProductRepository
} 