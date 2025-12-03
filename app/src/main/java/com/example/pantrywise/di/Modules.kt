package com.example.pantrywise.di

import com.example.pantrywise.view.navigation.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object NavigationModule {
    @Provides
    @ActivityRetainedScoped
    fun provideNavigationManager(): NavigationManager = NavigationManager()
}