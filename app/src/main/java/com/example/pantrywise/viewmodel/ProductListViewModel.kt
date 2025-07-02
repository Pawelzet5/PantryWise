package com.example.pantrywise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrywise.data.repository.IProductRepository
import com.example.pantrywise.model.dataclass.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: IProductRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.getProducts().collect { products ->
                    _products.value = products
                    // Seed sample data if database is empty
                    if (products.isEmpty()) {
                        repository.seedSampleData()
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load products: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            try {
                repository.addProduct(product)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to add product: ${e.message}"
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
                _errorMessage.value = "Failed to delete product: ${e.message}"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
} 