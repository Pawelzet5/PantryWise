package com.example.pantrywise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrywise.data.repository.IProductRepository
import com.example.pantrywise.model.dataclass.Product
import com.example.pantrywise.model.enums.ProductStatus
import com.example.pantrywise.util.LogUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: IProductRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getProducts().collect { products ->
                LogUtils.debug(products.toString())
                if (products.isEmpty())
                    repository.seedSampleData() // TODO(Remove when solid)
                else
                    _products.value = products
            }
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            try {
                repository.addProduct(product)
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            try {
                repository.updateProduct(product)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update product: ${e.message}"
            }
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            try {
                repository.deleteProduct(product)
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun moveProductToShoppingList(product: Product) {
        viewModelScope.launch {
            try {
                repository.updateProduct(
                    product.copy(productStatus = ProductStatus.SHOPPING_LIST)
                )
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
} 