package com.kemalcodes.composetutorial

// Tutorial #8: Navigation — Moving Between Screens
// https://kemalcodes.com/posts/jetpack-compose-tutorial-navigation/
//
// This tutorial demonstrates:
// - Type-safe navigation with @Serializable routes
// - Bottom navigation bar with NavigationBar + NavigationBarItem
// - Nested navigation (list → detail inside a tab)
// - Passing arguments between screens
// - Back stack management (popUpTo, launchSingleTop, restoreState)
//
// File structure:
// - Routes.kt — all route definitions (@Serializable)
// - Screens.kt — screen Composables (don't know about NavController)
// - AppNavigation.kt — NavHost + bottom bar + navigation logic
// - MainActivity.kt — entry point (this file)

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kemalcodes.composetutorial.navigation.AppNavigation
import com.kemalcodes.composetutorial.ui.theme.AndroidjetpackcomposetutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidjetpackcomposetutorialTheme {
                // All navigation is handled inside AppNavigation
                AppNavigation()
            }
        }
    }
}
