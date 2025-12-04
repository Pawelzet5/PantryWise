package com.example.pantrywise.view.productlist

import com.example.pantrywise.model.dataclass.Product
import com.example.pantrywise.model.enums.ProductCategory

data class ProductListState(
    val categoryToProductListMap: Map<ProductCategory, List<Product>> = emptyMap(),
    val selectedProduct: Product? = null,
    val expandedCategories: Set<ProductCategory> = emptySet(),
    val errorMessage: String? = null
)
