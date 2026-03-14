package com.kemalcodes.composetutorial

// Tutorial #1: What is Jetpack Compose and Why Should You Care?
// https://kemalcodes.com/posts/jetpack-compose-tutorial-what-is-compose/
//
// This file demonstrates:
// - setContent {} as the entry point for Compose (replaces setContentView)
// - @Composable functions that describe UI
// - @Preview to see your UI in Android Studio without running the app
// - Composables that take parameters
// - Composables that call other Composables
// - Building a simple ProfileCard component

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kemalcodes.composetutorial.ui.theme.AndroidjetpackcomposetutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // setContent replaces the old setContentView(R.layout.activity_main)
        // Everything inside this block is Compose UI
        setContent {
            AndroidjetpackcomposetutorialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Our home screen — built by combining Composables
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// --- Basic Composable Functions ---

// A simple Composable that shows a welcome message
// Composable functions always start with a capital letter
@Composable
fun WelcomeMessage() {
    Text("Welcome to Jetpack Compose!")
}

// Composables can take parameters — just like any Kotlin function
@Composable
fun Greeting(name: String) {
    Text("Hello, $name!")
}

// A Composable that shows user info
// It takes two parameters and arranges them vertically
@Composable
fun UserCard(name: String, email: String) {
    Column {
        Text(text = name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = email, fontSize = 14.sp)
    }
}

// --- Combining Composables ---

// You build complex screens by combining simple Composables
// This is the main screen of our app
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile card at the top
        ProfileCard(name = "Kemal", title = "Android Developer")

        Spacer(modifier = Modifier.height(32.dp))

        // Welcome message below
        WelcomeMessage()

        Spacer(modifier = Modifier.height(16.dp))

        // Two user cards
        UserCard(name = "Alex", email = "alex@example.com")
        Spacer(modifier = Modifier.height(8.dp))
        UserCard(name = "Sarah", email = "sarah@example.com")
    }
}

// --- Profile Card Component ---

// A reusable profile card that shows:
// - A circle with the first letter of the name
// - The name in bold
// - The title in smaller text
@Composable
fun ProfileCard(name: String, title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Circle with the first letter
        // We use firstOrNull() to avoid crashes if name is empty
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.firstOrNull()?.toString() ?: "?",
                color = Color.White,
                fontSize = 32.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Name
        Text(
            text = name,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        // Title
        Text(
            text = title,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// --- Previews ---

// Preview lets you see your UI in Android Studio without running the app
// Click the "Split" or "Design" tab to see it

// Light mode preview
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun HomeScreenPreview() {
    AndroidjetpackcomposetutorialTheme {
        HomeScreen()
    }
}

// Dark mode preview — same component, different theme
@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeScreenDarkPreview() {
    AndroidjetpackcomposetutorialTheme {
        HomeScreen()
    }
}

// You can preview individual components too
@Preview(showBackground = true, name = "Profile Card Only")
@Composable
fun ProfileCardPreview() {
    AndroidjetpackcomposetutorialTheme {
        ProfileCard(name = "Kemal", title = "Android Developer")
    }
}

// Preview with system UI (status bar, navigation bar)
@Preview(showSystemUi = true, name = "Full Screen")
@Composable
fun FullScreenPreview() {
    AndroidjetpackcomposetutorialTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            HomeScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}
