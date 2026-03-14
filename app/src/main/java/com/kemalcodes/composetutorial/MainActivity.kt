package com.kemalcodes.composetutorial

// Tutorial #10: MVI — Keep Your App Simple and Clean
// https://kemalcodes.com/posts/mvi-with-jetpack-compose/
//
// This tutorial demonstrates the MVI (Model-View-Intent) pattern:
// - CounterState.kt — the state (data the UI shows)
// - CounterIntent.kt — the intents (what the user wants to do)
// - CounterViewModel.kt — the brain (receives intents, creates new states)
// - CounterScreen.kt — the UI (reads state, sends intents)
//
// The MVI data flow:
// User taps → UI sends Intent → ViewModel creates new State → UI updates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.kemalcodes.composetutorial.counter.CounterScreen
import com.kemalcodes.composetutorial.ui.theme.AndroidjetpackcomposetutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidjetpackcomposetutorialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CounterScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
