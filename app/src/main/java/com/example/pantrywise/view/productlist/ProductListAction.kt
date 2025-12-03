package com.example.pantrywise.view.productlist

import com.example.pantrywise.model.dataclass.Product
import com.example.pantrywise.model.enums.ProductCategory

sealed interface ProductListAction {
    data class OnExpandCategoryClick(val productCategory: ProductCategory): ProductListAction
    data class OnProductSelected(val product: Product): ProductListAction
    data object OnProductDeselected: ProductListAction
    data object OnEditProductClick: ProductListAction
    data object OnRemoveProductClick: ProductListAction
    data object OnMoveProductClick: ProductListAction
}