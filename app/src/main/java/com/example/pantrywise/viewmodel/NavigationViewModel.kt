package com.example.pantrywise.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pantrywise.view.navigation.AppNavKey
import com.example.pantrywise.view.navigation.InventoryScreenKey
import com.example.pantrywise.view.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val navigationManager: NavigationManager
) : ViewModel() {

    val backStack: StateFlow<List<AppNavKey>> = navigationManager.backStack

    fun push(key: AppNavKey) = navigationManager.push(key)
    fun pop() = navigationManager.pop()
    fun replace(key: AppNavKey) = navigationManager.replace(key)

    fun leaveWelcomeScreen() = push(InventoryScreenKey)
}