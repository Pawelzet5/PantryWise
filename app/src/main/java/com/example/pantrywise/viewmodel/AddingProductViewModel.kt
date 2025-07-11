package com.example.pantrywise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrywise.data.repository.IProductRepository
import com.example.pantrywise.data.repository.ISuggestionsRepository
import com.example.pantrywise.view.ProductInputState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddingProductViewModel @Inject constructor(
    private val suggestionsRepository: ISuggestionsRepository,
    private val productRepository: IProductRepository
) : ViewModel() {
    private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    val suggestions: StateFlow<List<String>> = _suggestions

    val queryText = MutableStateFlow("")

    private val _selectedProducts = MutableStateFlow<List<ProductInputState>>(emptyList())
    val selectedProducts: StateFlow<List<ProductInputState>> = _selectedProducts

    init {
        viewModelScope.launch(Dispatchers.IO) {
            queryText
                .filter { validateQuery(it) }
                .debounce(400)
                .distinctUntilChanged()
                .collect {
                    val filtered = suggestionsRepository.getSuggestions(it)
                    _suggestions.value = filtered
                }
        }
    }

    fun onQueryUpdate(input: String) = viewModelScope.launch {
        queryText.value = input
    }

    fun onSuggestionSelected(suggestionName: String) = viewModelScope.launch {
        _selectedProducts.emit(selectedProducts.value.plus(ProductInputState(name = suggestionName)))
    }

    fun onProductInputStateChange(index: Int, productInputState: ProductInputState) =
        viewModelScope.launch {
            val currentList = selectedProducts.value.toMutableList()
            if (index in currentList.indices) {
                currentList[index] = productInputState
                _selectedProducts.emit(currentList.toList())
            }
        }

    fun handleRemoveProductInput(index: Int) = viewModelScope.launch {
        val currentList = selectedProducts.value.toMutableList()
        if (index in currentList.indices) {
            currentList.removeAt(index)
            _selectedProducts.emit(currentList)
        }
    }


    fun handleCancelAdding() = viewModelScope.launch {
        _selectedProducts.emit(emptyList())
    }

    fun handleAddProducts() = viewModelScope.launch {
        selectedProducts.value
            .filter { it.isValid() }
            .forEach { productRepository.addProduct(it.toProduct()) }
    }

    private fun validateQuery(input: String) = input.length >= 2
} 