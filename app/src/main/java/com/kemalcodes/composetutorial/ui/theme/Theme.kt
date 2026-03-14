package com.kemalcodes.composetutorial.ui.theme

// Tutorial #7: Material 3 Theming
// The theme combines colors + typography and applies to the entire app.
// isSystemInDarkTheme() checks if the user has dark mode enabled on their phone.

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Dark color scheme — lighter colors for dark backgrounds
private val DarkColorScheme = darkColorScheme(
    primary = Blue80,
    secondary = BlueGrey80,
    tertiary = Teal80,
    background = Color(0xFF121212),
    surface = SurfaceDark,
    onPrimary = Color(0xFF003258),
    onSecondary = Color(0xFF1C3D5A),
    onBackground = Color(0xFFE6E1E5),
    onSurface = Color(0xFFE6E1E5),
    error = ErrorRed,
)

// Light color scheme — darker colors for light backgrounds
private val LightColorScheme = lightColorScheme(
    primary = Blue40,
    secondary = BlueGrey40,
    tertiary = Teal40,
    background = Color.White,
    surface = SurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    error = ErrorRed,
)

@Composable
fun AndroidjetpackcomposetutorialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set to true to use wallpaper colors on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Dynamic colors from wallpaper (Android 12+)
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        // Our custom dark colors
        darkTheme -> DarkColorScheme
        // Our custom light colors
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
