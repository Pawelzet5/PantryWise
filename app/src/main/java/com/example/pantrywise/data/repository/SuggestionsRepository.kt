package com.example.pantrywise.data.repository

import android.content.Context
import com.example.pantrywise.util.FileHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class SuggestionsRepository(private val context: Context) : ISuggestionsRepository {
    private var allProductNames: List<String>? = null

    override suspend fun getAllProductNames(): List<String> = withContext(Dispatchers.IO) {
        if (allProductNames == null) {
            val jsonString = FileHelper.readAssetFile(context, "productNameSuggestions.json")
            val json = JSONObject(jsonString)
            val categories = json.getJSONArray("categories")
            val names = mutableListOf<String>()
            for (i in 0 until categories.length()) {
                val category = categories.getJSONObject(i)
                val products = category.getJSONArray("products")
                for (j in 0 until products.length()) {
                    val product = products.getJSONObject(j)
                    names.add(product.getString("name"))
                }
            }
            allProductNames = names
        }
        allProductNames ?: emptyList()
    }

    override suspend fun getSuggestions(input: String): List<String> {
        val allNames = getAllProductNames()
        return if (input.isBlank()) emptyList() else allNames.filter { it.contains(input, ignoreCase = true) }
    }
} 