package com.example.pantrywise.data.repository

import com.example.pantrywise.model.dataclass.Product
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    fun getProducts(): Flow<List<Product>>
    suspend fun addProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun seedSampleData()
} 