// MainActivity.kt — The entry point of our task manager app.
// @AndroidEntryPoint lets Hilt inject dependencies into this activity.
// We set up the theme and hand control to the navigation graph.
package com.kemalcodes.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kemalcodes.composetutorial.ui.navigation.AppNavigation
import com.kemalcodes.composetutorial.ui.theme.AndroidjetpackcomposetutorialTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidjetpackcomposetutorialTheme {
                AppNavigation()
            }
        }
    }
}
