package com.example.pantrywise.model.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pantrywise.model.dataclass.Product
import com.example.pantrywise.model.db.DatabaseConstants
import com.example.pantrywise.model.enums.ProductCategory
import com.example.pantrywise.model.enums.ProductUnit

@Entity(tableName = DatabaseConstants.PRODUCT_TABLE)
data class DbProduct(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    var quantity: Double,
    var productUnit: ProductUnit,
    var category: ProductCategory,
    var expirationDate: Long? = null
) {
    companion object {
        fun fromProduct(product: Product) = DbProduct(
            id = product.id,
            name = product.name,
            quantity = product.quantity,
            productUnit = product.productUnit,
            category = product.category,
            expirationDate = product.expirationDate
        )
    }
    
    fun toProduct(): Product = Product(
        id = id,
        name = name,
        quantity = quantity,
        productUnit = productUnit,
        category = category,
        expirationDate = expirationDate
    )
}
