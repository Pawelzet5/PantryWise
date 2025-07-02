package com.example.pantrywise.data.repository

import com.example.pantrywise.model.dataclass.Product
import com.example.pantrywise.model.db.dao.ProductDao
import com.example.pantrywise.model.db.entities.DbProduct
import com.example.pantrywise.model.enums.ProductCategory
import com.example.pantrywise.model.enums.ProductUnit
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
        val sampleProducts = listOf(
            Product(name = "Milk", quantity = 2.0, productUnit = ProductUnit.LITER, category = ProductCategory.BEVERAGES),
            Product(name = "Bread", quantity = 1.0, productUnit = ProductUnit.PIECE, category = ProductCategory.BAKERY),
            Product(name = "Apples", quantity = 6.0, productUnit = ProductUnit.PIECE, category = ProductCategory.PRODUCE),
            Product(name = "Chicken Breast", quantity = 500.0, productUnit = ProductUnit.GRAM, category = ProductCategory.MEAT_SEAFOOD),
            Product(name = "Rice", quantity = 1.0, productUnit = ProductUnit.KILOGRAM, category = ProductCategory.GRAINS_CEREALS),
            Product(name = "Olive Oil", quantity = 250.0, productUnit = ProductUnit.MILLILITER, category = ProductCategory.OILS_FATS),
            Product(name = "Salt", quantity = 100.0, productUnit = ProductUnit.GRAM, category = ProductCategory.SPICES_SEASONINGS),
            Product(name = "Eggs", quantity = 12.0, productUnit = ProductUnit.PIECE, category = ProductCategory.DAIRY_EGGS),
            Product(name = "Cheese", quantity = 200.0, productUnit = ProductUnit.GRAM, category = ProductCategory.DAIRY_EGGS),
            Product(name = "Tomatoes", quantity = 4.0, productUnit = ProductUnit.PIECE, category = ProductCategory.PRODUCE)
        )
        
        sampleProducts.forEach { product ->
            addProduct(product)
        }
    }
} 