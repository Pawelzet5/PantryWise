package com.example.pantrywise.view.navigation

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@ActivityRetainedScoped
class NavigationManager @Inject constructor() {
    private val _backStack = MutableStateFlow<List<AppNavKey>>(listOf(WelcomeScreenKey))
    val backStack: StateFlow<List<AppNavKey>> = _backStack.asStateFlow()

    fun push(key: AppNavKey) {
        _backStack.update { it + key }
    }

    fun pop() {
        _backStack.update { stack ->
            if (stack.size > 1) stack.dropLast(1) else stack
        }
    }

    fun replace(key: AppNavKey) {
        _backStack.update { stack ->
            stack.dropLast(1) + key
        }
    }

    fun popUntil(predicate: (AppNavKey) -> Boolean) {
        _backStack.update { stack ->
            val index = stack.indexOfLast(predicate)
            if (index != -1) stack.take(index + 1) else stack
        }
    }
}
