package com.example.pantrywise.di

import android.content.Context
import androidx.room.Room
import com.example.pantrywise.model.db.DatabaseConstants
import com.example.pantrywise.model.db.PantryWiseDatabase
import com.example.pantrywise.model.db.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PantryWiseDatabase {
        return Room.databaseBuilder(
            context,
            PantryWiseDatabase::class.java,
            DatabaseConstants.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: PantryWiseDatabase): ProductDao {
        return database.productDao()
    }
} 