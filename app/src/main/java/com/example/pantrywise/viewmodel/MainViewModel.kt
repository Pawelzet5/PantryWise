package com.example.pantrywise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrywise.R
import com.example.pantrywise.view.navigation.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigationManager: NavigationManager
) : ViewModel() {
    private val currentNavKey = navigationManager.backStack.map { it.last() }

    val scaffoldState = currentNavKey
        .map(::makeMainScaffoldState)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            makeMainScaffoldState(navigationManager.backStack.value.last())
        )

    fun onAction(action: MainScaffoldAction) {
        when (action) {
            is MainScaffoldAction.OnAddClick -> handleAddClick(action.navKey)
            MainScaffoldAction.OnBackClick -> handleBackClick()
        }
    }

    private fun handleAddClick(navKey: AppNavKey) {
        navigationManager.push(navKey)
    }

    private fun handleBackClick() {
        navigationManager.pop()
    }

    private fun makeMainScaffoldState(currentNavKey: AppNavKey) = MainScaffoldState(
        showBackButton = currentNavKey.showBackButton(),
        showAddButton = currentNavKey.showAddButton(),
        topBarTextResId = currentNavKey.getToolbarTextResId()
    )
}

data class MainScaffoldState(
    val showBackButton: Boolean = false,
    val showAddButton: Boolean = false,
    val topBarTextResId: Int?
)

sealed interface MainScaffoldAction {
    data class OnAddClick(val navKey: AppNavKey) : MainScaffoldAction
    data object OnBackClick : MainScaffoldAction
}

fun AppNavKey.showBackButton() = when (this) {
    is AddingProductScreenKey, AddingProductWithCameraScreenKey -> true
    else -> false
}

fun AppNavKey.showAddButton() = when (this) {
    is InventoryScreenKey, ShoppingListScreenKey -> true
    else -> false
}

fun AppNavKey.getToolbarTextResId(): Int? = when (this) {
    AddingProductScreenKey -> R.string.adding_product_header
    AddingProductWithCameraScreenKey -> R.string.adding_product_header
    InventoryScreenKey -> R.string.inventory_header
    ShoppingListScreenKey -> R.string.shopping_list_header
    WelcomeScreenKey -> null
}