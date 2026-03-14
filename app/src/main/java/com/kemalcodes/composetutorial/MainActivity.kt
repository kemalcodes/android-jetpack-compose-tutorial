package com.kemalcodes.composetutorial

// Tutorial #12: Retrofit — Loading Data from APIs
// https://kemalcodes.com/posts/jetpack-compose-tutorial-retrofit/
//
// This tutorial demonstrates:
// - Retrofit setup with Kotlin Serialization
// - API interface with @GET
// - ViewModel with loading/error/success states
// - UI that shows spinner, error with retry, or user list
// - Real API call to jsonplaceholder.typicode.com

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.kemalcodes.composetutorial.api.UserListScreen
import com.kemalcodes.composetutorial.ui.theme.AndroidjetpackcomposetutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidjetpackcomposetutorialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserListScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
