package com.example.pantrywise.util

import android.util.Log

object LogUtils {
    private const val DEFAULT_TAG = "PantryWiseApp"

    fun debug(message: String, tag: String = DEFAULT_TAG) {
        Log.d(tag, message)
    }

    fun info(message: String, tag: String = DEFAULT_TAG) {
        Log.i(tag, message)
    }

    fun warn(message: String, tag: String = DEFAULT_TAG) {
        Log.w(tag, message)
    }

    fun error(message: String, tag: String = DEFAULT_TAG, throwable: Throwable? = null) {
        Log.e(tag, message, throwable)
    }
} 