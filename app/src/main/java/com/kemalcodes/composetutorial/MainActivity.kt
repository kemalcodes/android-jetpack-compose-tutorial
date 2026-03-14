package com.kemalcodes.composetutorial

// Tutorial #9: ViewModel — Managing Screen Logic
// https://kemalcodes.com/posts/jetpack-compose-tutorial-viewmodel/
//
// This tutorial demonstrates:
// - ViewModel with StateFlow for screen state
// - collectAsStateWithLifecycle to observe state in Compose
// - One state object per screen (UserListState)
// - Loading, error, and success states
// - Search filtering with ViewModel
// - Delete action from UI to ViewModel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.kemalcodes.composetutorial.ui.theme.AndroidjetpackcomposetutorialTheme
import com.kemalcodes.composetutorial.userlist.UserListScreen

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
