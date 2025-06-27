package com.example.pantrywise.model.dataclass

import com.example.pantrywise.model.enums.ProductUnit


data class Product(
    val name: String,
    var quantity: Double,
    var productUnit: ProductUnit
)
