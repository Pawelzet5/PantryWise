package com.example.pantrywise

import com.example.pantrywise.model.dataclass.Product
import com.example.pantrywise.model.enums.ProductCategory
import com.example.pantrywise.model.enums.ProductUnit

object TestDataFactory {
    fun prepareProduct() = Product(name = "Milk", quantity = 2.0, productUnit = ProductUnit.LITER, category = ProductCategory.BEVERAGES)
}