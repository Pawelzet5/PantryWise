package com.example.pantrywise.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// --- Light Theme Colors ---
private val LightColorScheme = lightColorScheme(
    primary = Green500,
    onPrimary = WhiteText,
    primaryContainer = Green100,
    onPrimaryContainer = BlackText,
    secondary = Amber600,
    onSecondary = BlackText,
    secondaryContainer = Amber50,
    onSecondaryContainer = BlackText,
    background = Grey50,
    onBackground = Grey900,
    surface = White,
    onSurface = Grey900Surface,
    error = RedError,
    onError = WhiteOnError,
)

// --- Dark Theme Colors ---
private val DarkColorScheme = darkColorScheme(
    primary = Green300,
    onPrimary = Green900,
    primaryContainer = Green700,
    secondary = Amber300,
    onSecondary = BlackTextDark,
    secondaryContainer = Amber600Dark,
    background = TrueBlack,
    onBackground = Grey300,
    surface = DarkGrey,
    onSurface = Grey300Surface,
    error = Red200,
    onError = Red900,
)

/**
 * PantryWiseTheme applies the color scheme and typography to your app.
 *
 * Usage:
 * - Wrap your entire app (or each screen) in PantryWiseTheme.
 * - Use MaterialTheme.colorScheme.primary, .secondary, etc. for colors in your UI.
 * - Use MaterialTheme.typography for text styles.
 * - Use MaterialTheme.shapes for shapes.
 *
 * Example:
 * ```kotlin
 * PantryWiseTheme {
 *     Surface(color = MaterialTheme.colorScheme.background) {
 *         Text("Hello", color = MaterialTheme.colorScheme.primary)
 *     }
 * }
 * ```
 *
 * Compose will automatically switch between light and dark themes based on the system setting,
 * unless you override [darkTheme].
 */
@Composable
fun PantryWiseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}