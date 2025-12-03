package com.example.pantrywise.view.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface AppNavKey: NavKey

@Serializable
data object WelcomeScreenKey : AppNavKey

@Serializable
data object InventoryScreenKey : AppNavKey

@Serializable
data object ShoppingListScreenKey : AppNavKey

@Serializable
data object AddingProductScreenKey : AppNavKey

@Serializable
data object AddingProductWithCameraScreenKey : AppNavKey
