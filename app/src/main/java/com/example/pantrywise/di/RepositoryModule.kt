package com.example.pantrywise.di

import android.content.Context
import com.example.pantrywise.data.repository.IProductRepository
import com.example.pantrywise.data.repository.ProductRepository
import com.example.pantrywise.data.repository.SuggestionsRepository
import com.example.pantrywise.data.repository.ISuggestionsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// For interface bindings
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindingModule {
    @Binds
    abstract fun bindProductRepository(impl: ProductRepository): IProductRepository

    @Binds
    abstract fun bindSuggestionsRepository(impl: SuggestionsRepository): ISuggestionsRepository
}

// For SuggestionsRepository provider
@Module
@InstallIn(SingletonComponent::class)
object SuggestionsRepositoryModule {
    @Provides
    @Singleton
    fun provideSuggestionsRepository(@ApplicationContext context: Context): SuggestionsRepository {
        return SuggestionsRepository(context)
    }
} 