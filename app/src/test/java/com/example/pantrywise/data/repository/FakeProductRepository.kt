package com.example.pantrywise.data.repository

import com.example.pantrywise.model.dataclass.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeProductRepository : IProductRepository {
    private val productsFlow = MutableStateFlow<List<Product>>(emptyList())
    private val products = mutableListOf<Product>()
    private var nextId = 1

    override fun getProducts(): Flow<List<Product>> = productsFlow.asStateFlow()

    override suspend fun addProduct(product: Product) {
        val newProduct = product.copy(id = nextId++)
        products.add(newProduct)
        productsFlow.value = products.toList()
    }

    override suspend fun updateProduct(product: Product) {
        val index = products.indexOfFirst { it.id == product.id }
        if (index != -1) {
            products[index] = product
            productsFlow.value = products.toList()
        }
    }

    override suspend fun deleteProduct(product: Product) {
        products.removeAll { it.id == product.id }
        productsFlow.value = products.toList()
    }

    override suspend fun seedSampleData() {
        // No-op for tests, or you can add test data here if needed
    }
} 