package com.example.pantrywise.view.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrywise.data.repository.IProductRepository
import com.example.pantrywise.model.enums.ProductStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: IProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getProducts().collect { products ->
                if (products.isEmpty())
                    repository.seedSampleData() // TODO(Remove when version is solid and functional)
                else
                    _state.update {
                        it.copy(
                            categoryToProductListMap = products.groupBy { product -> product.category }
                        )
                    }
            }
        }
    }

    fun onAction(action: ProductListAction) = when (action) {
        ProductListAction.OnEditProductClick -> {
            //TODO()
        }

        ProductListAction.OnRemoveProductClick -> deleteProduct()
        ProductListAction.OnMoveProductClick -> moveProductToShoppingList()
        ProductListAction.OnProductDeselected -> _state.update { it.copy(selectedProduct = null) }


        is ProductListAction.OnExpandCategoryClick -> _state.update {
                it.copy(
                    expandedCategories = it.expandedCategories.toggle(action.productCategory)
                )
            }

        is ProductListAction.OnProductSelected -> _state.update {
            it.copy(selectedProduct = action.product)
        }
    }

    private fun deleteProduct() {
        viewModelScope.launch {
            try {
                state.value.selectedProduct?.let { product ->
                    repository.deleteProduct(product)
                }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    private fun moveProductToShoppingList() {
        viewModelScope.launch {
            try {
                state.value.selectedProduct?.let { product ->
                    repository.updateProduct(
                        product.copy(productStatus = ProductStatus.SHOPPING_LIST)
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e.message) }
            }
        }
    }
}

fun <T> Set<T>.toggle(value: T): Set<T> =
    if (this.contains(value))
        this.minus(value)
    else this.plus(value)
