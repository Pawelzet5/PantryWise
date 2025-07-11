package com.example.pantrywise.data.repository

interface ISuggestionsRepository {
    suspend fun getAllProductNames(): List<String>
    suspend fun getSuggestions(input: String): List<String>
} 