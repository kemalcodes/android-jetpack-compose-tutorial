package com.kemalcodes.composetutorial

// Tutorial #14: Dependency Injection with Hilt — Main Activity
// This file demonstrates the @AndroidEntryPoint annotation.
// @AndroidEntryPoint tells Hilt that this Activity can receive injected dependencies.
// Any ViewModel used in this Activity (or its Composables) can now use @HiltViewModel.
// Note: This branch uses AGP 8.10 + Kotlin 2.1.21 + KSP for Hilt/Room compatibility

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.kemalcodes.composetutorial.notes.NotesScreen
import com.kemalcodes.composetutorial.ui.theme.AndroidjetpackcomposetutorialTheme
import dagger.hilt.android.AndroidEntryPoint

// @AndroidEntryPoint marks this Activity as a Hilt injection target.
// Without this annotation, Hilt cannot inject dependencies into this Activity
// or provide @HiltViewModel instances to Composables inside it.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidjetpackcomposetutorialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NotesScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
