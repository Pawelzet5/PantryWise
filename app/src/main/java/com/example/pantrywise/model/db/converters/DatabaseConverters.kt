package com.example.pantrywise.model.db.converters

import androidx.room.TypeConverter
import com.example.pantrywise.model.enums.ProductCategory
import com.example.pantrywise.model.enums.ProductUnit

class DatabaseConverters {

    @TypeConverter
    fun fromProductUnit(productUnit: ProductUnit): Int {
        return productUnit.ordinal
    }

    @TypeConverter
    fun toProductUnit(ordinal: Int): ProductUnit {
        return ProductUnit.entries[ordinal]
    }

    @TypeConverter
    fun fromProductCategory(productCategory: ProductCategory): Int {
        return productCategory.ordinal
    }

    @TypeConverter
    fun toProductCategory(ordinal: Int): ProductCategory {
        return ProductCategory.entries[ordinal]
    }
} 