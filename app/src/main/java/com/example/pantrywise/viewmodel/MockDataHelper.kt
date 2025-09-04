package com.example.pantrywise.viewmodel

import android.icu.util.Calendar
import com.example.pantrywise.model.dataclass.Product
import com.example.pantrywise.model.enums.ProductCategory
import com.example.pantrywise.model.enums.ProductUnit

object MockDataHelper {
    fun getMockProductList() = listOf(
        Product(
            name = "Milk",
            details = "3.2%",
            quantity = 2.0,
            productUnit = ProductUnit.LITER,
            category = ProductCategory.BEVERAGES,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*3)
        ),
        Product(
            name = "Bread",
            details = "",
            quantity = 1.0,
            productUnit = ProductUnit.PIECE,
            category = ProductCategory.BAKERY,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*3)
        ),
        Product(
            name = "Burger Buns",
            details = "",
            quantity = 4.0,
            productUnit = ProductUnit.PIECE,
            category = ProductCategory.BAKERY,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*1)
        ),
        Product(
            name = "Grahamki",
            details = "",
            quantity = 5.0,
            productUnit = ProductUnit.PIECE,
            category = ProductCategory.BAKERY,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*2)
        ),
        Product(
            name = "Apples",
            details = "",
            quantity = 6.0,
            productUnit = ProductUnit.PIECE,
            category = ProductCategory.PRODUCE,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*14)
        ),
        Product(
            name = "Chicken Breast",
            details = "",
            quantity = 500.0,
            productUnit = ProductUnit.GRAM,
            category = ProductCategory.MEAT_SEAFOOD,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*3)
        ),
        Product(
            name = "Rice",
            details = "",
            quantity = 1.0,
            productUnit = ProductUnit.KILOGRAM,
            category = ProductCategory.GRAINS_CEREALS
        ),
        Product(
            name = "Olive Oil",
            details = "",
            quantity = 250.0,
            productUnit = ProductUnit.MILLILITER,
            category = ProductCategory.OILS_FATS
        ),
        Product(
            name = "Salt",
            details = "",
            quantity = 100.0,
            productUnit = ProductUnit.GRAM,
            category = ProductCategory.SPICES_SEASONINGS
        ),
        Product(
            name = "Eggs",
            details = "",
            quantity = 12.0,
            productUnit = ProductUnit.PIECE,
            category = ProductCategory.DAIRY_EGGS,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*30)
        ),
        Product(
            name = "Cheese",
            details = "",
            quantity = 200.0,
            productUnit = ProductUnit.GRAM,
            category = ProductCategory.DAIRY_EGGS,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*8)
        ),
        Product(
            name = "Mozzarella",
            details = "Fior di Latte",
            quantity = 200.0,
            productUnit = ProductUnit.GRAM,
            category = ProductCategory.DAIRY_EGGS,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*3)
        ),
        Product(
            name = "Mozzarella",
            details = "Lidlowa",
            quantity = 2.0,
            productUnit = ProductUnit.BAG,
            category = ProductCategory.DAIRY_EGGS,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*3)
        ),
        Product(
            name = "Cheddar",
            details = "",
            quantity = 200.0,
            productUnit = ProductUnit.GRAM,
            category = ProductCategory.DAIRY_EGGS,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*14)
        ),
        Product(
            name = "Gouda",
            details = "",
            quantity = 200.0,
            productUnit = ProductUnit.GRAM,
            category = ProductCategory.DAIRY_EGGS,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*14)
        ),
        Product(
            name = "Monterey Jack",
            details = "",
            quantity = 200.0,
            productUnit = ProductUnit.GRAM,
            category = ProductCategory.DAIRY_EGGS,
            expirationDate = Calendar.getInstance().timeInMillis.minus(86400000*8)
        ),
        Product(
            name = "Burrata",
            details = "",
            quantity = 200.0,
            productUnit = ProductUnit.GRAM,
            category = ProductCategory.DAIRY_EGGS,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*2)
        ),
        Product(
            name = "Tomatoes",
            details = "Włoskie Mutti",
            quantity = 4.0,
            productUnit = ProductUnit.PIECE,
            category = ProductCategory.PRODUCE,
            expirationDate = Calendar.getInstance().timeInMillis.plus(86400000*6)
        )
    )
}