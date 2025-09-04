package com.example.pantrywise.view

import com.example.pantrywise.model.dataclass.Product
import com.example.pantrywise.model.enums.ProductCategory
import com.example.pantrywise.model.enums.ProductUnit


// State holder for a single product input
data class ProductInputState(
    val name: String = "",
    val details: String = "",
    val quantity: String = "",
    val expirationDate: Long? = null,
    val unit: ProductUnit = ProductUnit.PIECE,
    val category: ProductCategory = ProductCategory.OTHER
) {
    fun isValid() =
        name.isNotBlank() && quantity.toDoubleOrNull() != null && quantity.toDouble() > 0

    fun toProduct() = Product(
        name = name,
        details = details,
        quantity = quantity.toDoubleOrNull() ?: 1.0,
        productUnit = unit,
        category = category,
        expirationDate = expirationDate
    )
}