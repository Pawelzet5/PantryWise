package com.example.pantrywise.model.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.pantrywise.model.db.DatabaseConstants.PRODUCT_TABLE
import com.example.pantrywise.model.db.entities.DbProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM $PRODUCT_TABLE ORDER BY id ASC")
    fun getProductList(): Flow<List<DbProduct>>

    @Upsert
    suspend fun upsertProduct(dbProduct: DbProduct)

    @Delete
    suspend fun deleteProduct(dbProduct: DbProduct)
}