package com.example.pantrywise.model.dataclass

import com.example.pantrywise.model.enums.ProductCategory
import com.example.pantrywise.model.enums.ProductUnit


data class Product(
    val id: Int = 0,
    val name: String,
    var quantity: Double,
    var productUnit: ProductUnit,
    var category: ProductCategory = ProductCategory.OTHER,
    var expirationDate: Long? = null
)
