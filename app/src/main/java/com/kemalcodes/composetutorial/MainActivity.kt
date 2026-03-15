// MainActivity.kt — Entry point with dark mode state management.
// The dark mode preference is held here and passed down to the theme
// and settings screen so the user can toggle it at runtime.
package com.kemalcodes.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.kemalcodes.composetutorial.ui.navigation.AppNavigation
import com.kemalcodes.composetutorial.ui.theme.AndroidjetpackcomposetutorialTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Track the dark mode state so the user can toggle it from Settings
            var isDarkMode by rememberSaveable { mutableStateOf(false) }

            AndroidjetpackcomposetutorialTheme(darkTheme = isDarkMode) {
                AppNavigation(
                    isDarkMode = isDarkMode,
                    onDarkModeToggle = { isDarkMode = it }
                )
            }
        }
    }
}
