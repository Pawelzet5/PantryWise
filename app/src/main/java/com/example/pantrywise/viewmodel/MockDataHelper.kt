package com.example.pantrywise.viewmodel

import android.icu.util.Calendar
import com.example.pantrywise.model.dataclass.Product
import com.example.pantrywise.model.enums.ProductCategory
import com.example.pantrywise.model.enums.ProductUnit

object MockDataHelper {
    fun getMockProductList() = listOf(
        Product(
            name = "Milk",
            quantity = 2.0,
            productUnit = ProductUnit.LITER,
            category = ProductCategory.BEVERAGES,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*3)
        ),
        Product(
            name = "Bread",
            quantity = 1.0,
            productUnit = ProductUnit.PIECE,
            category = ProductCategory.BAKERY,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*3)
        ),
        Product(
            name = "Burger Buns",
            quantity = 4.0,
            productUnit = ProductUnit.PIECE,
            category = ProductCategory.BAKERY,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*1)
        ),
        Product(
            name = "Grahamki",
            quantity = 5.0,
            productUnit = ProductUnit.PIECE,
            category = ProductCategory.BAKERY,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*2)
        ),
        Product(
            name = "Apples",
            quantity = 6.0,
            productUnit = ProductUnit.PIECE,
            category = ProductCategory.PRODUCE,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*14)
        ),
        Product(
            name = "Chicken Breast",
            quantity = 500.0,
            productUnit = ProductUnit.GRAM,
            category = ProductCategory.MEAT_SEAFOOD,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*3)
        ),
        Product(
            name = "Rice",
            quantity = 1.0,
            productUnit = ProductUnit.KILOGRAM,
            category = ProductCategory.GRAINS_CEREALS
        ),
        Product(
            name = "Olive Oil",
            quantity = 250.0,
            productUnit = ProductUnit.MILLILITER,
            category = ProductCategory.OILS_FATS
        ),
        Product(
            name = "Salt",
            quantity = 100.0,
            productUnit = ProductUnit.GRAM,
            category = ProductCategory.SPICES_SEASONINGS
        ),
        Product(
            name = "Eggs",
            quantity = 12.0,
            productUnit = ProductUnit.PIECE,
            category = ProductCategory.DAIRY_EGGS,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*30)
        ),
        Product(
            name = "Cheese",
            quantity = 200.0,
            productUnit = ProductUnit.GRAM,
            category = ProductCategory.DAIRY_EGGS,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*8)
        ),
        Product(
            name = "Mozzarella",
            quantity = 200.0,
            productUnit = ProductUnit.GRAM,
            category = ProductCategory.DAIRY_EGGS,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*3)
        ),
        Product(
            name = "Cheddar",
            quantity = 200.0,
            productUnit = ProductUnit.GRAM,
            category = ProductCategory.DAIRY_EGGS,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*14)
        ),
        Product(
            name = "Gouda",
            quantity = 200.0,
            productUnit = ProductUnit.GRAM,
            category = ProductCategory.DAIRY_EGGS,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*14)
        ),
        Product(
            name = "Monterey Jack",
            quantity = 200.0,
            productUnit = ProductUnit.GRAM,
            category = ProductCategory.DAIRY_EGGS,
            expirationDate = Calendar.getInstance().timeInMillis.minus(86400000*8)
        ),
        Product(
            name = "Burrata",
            quantity = 200.0,
            productUnit = ProductUnit.GRAM,
            category = ProductCategory.DAIRY_EGGS,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*2)
        ),
        Product(
            name = "Tomatoes",
            quantity = 4.0,
            productUnit = ProductUnit.PIECE,
            category = ProductCategory.PRODUCE,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*6)
        )
    )
}