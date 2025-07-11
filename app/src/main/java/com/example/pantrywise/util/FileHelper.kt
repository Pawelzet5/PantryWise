package com.example.pantrywise.util

import android.content.Context

object FileHelper {
    fun readAssetFile(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
} 