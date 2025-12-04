package com.example.pantrywise.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.pantrywise.view.navigation.*
import com.example.pantrywise.viewmodel.NavigationViewModel


@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel
) {
    val backStack by navigationViewModel.backStack.collectAsStateWithLifecycle()
    NavDisplay(
        modifier = modifier.fillMaxSize(),
        onBack = { navigationViewModel.pop() },
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = { key ->
            when (key) {

                is WelcomeScreenKey -> {
                    NavEntry(key = key) {
                        // TODO
                    }
                }

                is InventoryScreenKey -> {
                    NavEntry(key = key) {
                        // TODO
                    }
                }

                is ShoppingListScreenKey -> {
                    NavEntry(key = key) {
                        // TODO
                    }
                }

                is AddingProductScreenKey -> {
                    NavEntry(key = key) {
                        // TODO
                    }
                }

                is AddingProductWithCameraScreenKey -> {
                    NavEntry(key = key) {
                        // TODO
                    }
                }
            }
        }
    )
}