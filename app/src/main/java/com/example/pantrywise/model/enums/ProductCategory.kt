package com.example.pantrywise.model.enums

import androidx.annotation.StringRes
import com.example.pantrywise.R

enum class ProductCategory(
    @StringRes val labelResId: Int
) {
    // Dairy & Eggs
    DAIRY_EGGS(R.string.category_dairy_eggs),
    
    // Beverages
    BEVERAGES(R.string.category_beverages),
    
    // Bakery
    BAKERY(R.string.category_bakery),
    
    // Meat & Seafood
    MEAT_SEAFOOD(R.string.category_meat_seafood),
    
    // Produce (Fruits & Vegetables)
    PRODUCE(R.string.category_produce),
    
    // Grains & Cereals
    GRAINS_CEREALS(R.string.category_grains_cereals),
    
    // Canned Goods
    CANNED_GOODS(R.string.category_canned_goods),
    
    // Condiments & Sauces
    CONDIMENTS_SAUCES(R.string.category_condiments_sauces),
    
    // Snacks & Sweets
    SNACKS_SWEETS(R.string.category_snacks_sweets),
    
    // Spices & Seasonings
    SPICES_SEASONINGS(R.string.category_spices_seasonings),
    
    // Oils & Fats
    OILS_FATS(R.string.category_oils_fats),
    
    // Frozen Foods
    FROZEN_FOODS(R.string.category_frozen_foods),
    
    // Pantry Staples
    PANTRY_STAPLES(R.string.category_pantry_staples),
    
    // Other
    OTHER(R.string.category_other);

    companion object {
        fun getMainCategories() = listOf(
            DAIRY_EGGS,
            BEVERAGES,
            BAKERY,
            MEAT_SEAFOOD,
            PRODUCE,
            GRAINS_CEREALS,
            CANNED_GOODS,
            CONDIMENTS_SAUCES,
            SNACKS_SWEETS,
            SPICES_SEASONINGS,
            OILS_FATS,
            FROZEN_FOODS,
            PANTRY_STAPLES
        )
    }
} 