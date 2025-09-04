package com.example.pantrywise.data.repository

import android.icu.util.Calendar
import com.example.pantrywise.model.dataclass.Product
import com.example.pantrywise.model.db.dao.ProductDao
import com.example.pantrywise.model.db.entities.DbProduct
import com.example.pantrywise.model.enums.ProductCategory
import com.example.pantrywise.model.enums.ProductUnit
import com.example.pantrywise.util.LogUtils
import com.example.pantrywise.viewmodel.MockDataHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productDao: ProductDao
) : IProductRepository {
    
    override fun getProducts(): Flow<List<Product>> {
        return productDao.getProductList().map { dbProducts ->
            dbProducts.map { it.toProduct() }
        }
    }
    
    override suspend fun addProduct(product: Product) {
        val dbProduct = DbProduct.fromProduct(product)
        productDao.upsertProduct(dbProduct)
    }
    
    override suspend fun updateProduct(product: Product) {
        val dbProduct = DbProduct.fromProduct(product)
        productDao.upsertProduct(dbProduct)
    }
    
    override suspend fun deleteProduct(product: Product) {
        val dbProduct = DbProduct.fromProduct(product)
        productDao.deleteProduct(dbProduct)
    }
    
    override suspend fun seedSampleData() {
        MockDataHelper.getMockProductList().forEach { product ->
            addProduct(product)
        }
    }
} 