package com.example.pantrywise.ui.extensions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pantrywise.model.enums.ProductCategory

/**
 * Extension function to get the appropriate Material Icon for a ProductCategory
 * This keeps the enum clean while providing UI functionality
 */
fun ProductCategory.getIcon(): ImageVector {
    return when (this) {
        ProductCategory.DAIRY_EGGS -> Icons.Default.Egg
        ProductCategory.BEVERAGES -> Icons.Default.LocalDrink
        ProductCategory.BAKERY -> Icons.Default.BreakfastDining
        ProductCategory.MEAT_SEAFOOD -> Icons.Default.Restaurant
        ProductCategory.PRODUCE -> Icons.Default.Eco
        ProductCategory.GRAINS_CEREALS -> Icons.Default.Grain
        ProductCategory.CANNED_GOODS -> Icons.Default.Inventory2
        ProductCategory.CONDIMENTS_SAUCES -> Icons.Default.Restaurant
        ProductCategory.SNACKS_SWEETS -> Icons.Default.Cake
        ProductCategory.SPICES_SEASONINGS -> Icons.Default.RestaurantMenu
        ProductCategory.OILS_FATS -> Icons.Default.Opacity
        ProductCategory.FROZEN_FOODS -> Icons.Default.AcUnit
        ProductCategory.PANTRY_STAPLES -> Icons.Default.Inventory
        ProductCategory.OTHER -> Icons.Default.Inventory
    }
} 