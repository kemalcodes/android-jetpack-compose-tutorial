// Tutorial #18: Testing Compose UI
// Simple screens (Counter + Login) designed to be tested with Compose UI tests.
// kemalcodes — https://kemalcodes.com

package com.kemalcodes.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kemalcodes.composetutorial.ui.theme.AndroidjetpackcomposetutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidjetpackcomposetutorialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TestingScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Main screen showing both testable composables
@Composable
fun TestingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionTitle("Testing Compose UI")
        Text(
            text = "These screens are designed to be tested with Compose UI testing APIs.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Counter screen — tests increment behavior
        SectionTitle("Counter Screen")
        CounterScreen()

        Spacer(modifier = Modifier.height(16.dp))

        // Login screen — tests button enabled/disabled state
        SectionTitle("Login Screen")
        LoginScreen()

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// A simple counter that increments on button press.
// Test verifies the count text updates correctly.
@Composable
fun CounterScreen(modifier: Modifier = Modifier) {
    var count by remember { mutableIntStateOf(0) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Display the current count — test finds this by text
            Text(
                text = "Count: $count",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Increment button — test clicks this and verifies count changes
            Button(onClick = { count++ }) {
                Text("Increment")
            }
        }
    }
}

// A login form with email and password fields.
// The login button is only enabled when both fields are non-empty.
// Tests verify the button's enabled/disabled state.
@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Button is only enabled when both fields have content
    val isFormValid = email.isNotBlank() && password.isNotBlank()

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Login button — disabled when fields are empty
            Button(
                onClick = { /* Login action */ },
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            // Status text showing whether the form is valid
            if (!isFormValid) {
                Text(
                    text = "Please fill in all fields",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// Section title helper
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}

@Preview(showBackground = true, name = "Light")
@Composable
fun TestingScreenPreviewLight() {
    AndroidjetpackcomposetutorialTheme(darkTheme = false, dynamicColor = false) {
        TestingScreen()
    }
}

@Preview(showBackground = true, name = "Dark")
@Composable
fun TestingScreenPreviewDark() {
    AndroidjetpackcomposetutorialTheme(darkTheme = true, dynamicColor = false) {
        TestingScreen()
    }
}
