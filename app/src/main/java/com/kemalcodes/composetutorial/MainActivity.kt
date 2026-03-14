package com.kemalcodes.composetutorial

// Tutorial #5: State — The Most Important Concept
// https://kemalcodes.com/posts/jetpack-compose-tutorial-state/
//
// This file demonstrates:
// - remember + mutableStateOf for keeping state
// - Recomposition — how Compose updates the UI
// - Boolean, String, Int, List state types
// - rememberSaveable — surviving screen rotation
// - State hoisting pattern
// - Profile editor with multiple state types

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kemalcodes.composetutorial.ui.theme.AndroidjetpackcomposetutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidjetpackcomposetutorialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StateExamplesScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// --- Main Screen ---

@Composable
fun StateExamplesScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("State Examples", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        // Counter with state hoisting
        SectionTitle("Counter (State Hoisting)")
        CounterSection()

        // Toggle
        SectionTitle("Toggle Switch")
        ToggleDemo()

        // Text input
        SectionTitle("Text Input")
        NameInput()

        // Todo list
        SectionTitle("Dynamic List")
        TodoList()

        // Profile editor
        SectionTitle("Profile Editor (rememberSaveable)")
        ProfileEditor()

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.primary
    )
}

// --- Counter with State Hoisting ---
// State lives in the parent (CounterSection)
// Counter is just a display — it receives state and sends events

@Composable
fun CounterSection() {
    // Parent owns the state
    var count by remember { mutableStateOf(0) }

    // Pass state DOWN, receive events UP
    Counter(
        count = count,
        onIncrement = { count++ },
        onDecrement = { count-- },
        onReset = { count = 0 }
    )
}

// This Composable is reusable — it doesn't own any state
@Composable
fun Counter(
    count: Int,                  // State comes DOWN from parent
    onIncrement: () -> Unit,     // Events go UP to parent
    onDecrement: () -> Unit,
    onReset: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "$count",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onDecrement) { Text("  −  ") }
            Button(onClick = onIncrement) { Text("  +  ") }
        }
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedButton(onClick = onReset) { Text("Reset") }
    }
}

// --- Toggle Demo ---

@Composable
fun ToggleDemo() {
    // Boolean state — true or false
    var isDarkMode by remember { mutableStateOf(false) }
    var notifications by remember { mutableStateOf(true) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isDarkMode) "Dark Mode: ON" else "Dark Mode: OFF",
                modifier = Modifier.weight(1f)
            )
            Switch(checked = isDarkMode, onCheckedChange = { isDarkMode = it })
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (notifications) "Notifications: ON" else "Notifications: OFF",
                modifier = Modifier.weight(1f)
            )
            Switch(checked = notifications, onCheckedChange = { notifications = it })
        }
    }
}

// --- Text Input ---

@Composable
fun NameInput() {
    // String state — changes as the user types
    var name by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Your name") },
            modifier = Modifier.fillMaxWidth()
        )
        // The greeting updates automatically when name changes
        if (name.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Hello, $name!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// --- Todo List ---

@Composable
fun TodoList() {
    // List state — items = items + newItem creates a NEW list
    // Never use items.add() — Compose won't detect the change
    var items by remember { mutableStateOf(listOf("Learn Compose", "Build an app")) }
    var newItem by remember { mutableStateOf("") }

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = newItem,
                onValueChange = { newItem = it },
                label = { Text("New item") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newItem.isNotBlank()) {
                        items = items + newItem  // New list, not mutation
                        newItem = ""
                    }
                }
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Display items with delete option
        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "• $item",
                    modifier = Modifier.weight(1f)
                )
                OutlinedButton(
                    onClick = {
                        // Remove item by creating a new list without it
                        items = items.filterIndexed { i, _ -> i != index }
                    }
                ) {
                    Text("✕", fontSize = 12.sp)
                }
            }
        }
    }
}

// --- Profile Editor ---
// Uses rememberSaveable so user input survives screen rotation

@Composable
fun ProfileEditor() {
    // rememberSaveable survives rotation, remember does not
    var name by rememberSaveable { mutableStateOf("") }
    var bio by rememberSaveable { mutableStateOf("") }
    var isPublic by rememberSaveable { mutableStateOf(true) }
    var saved by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it; saved = false },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it; saved = false },
            label = { Text("Bio") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Public profile", modifier = Modifier.weight(1f))
            Switch(
                checked = isPublic,
                onCheckedChange = { isPublic = it; saved = false }
            )
        }

        // Live preview
        if (name.isNotEmpty()) {
            Text("Preview", fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary)
            Text(text = name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            if (bio.isNotEmpty()) {
                Text(text = bio, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(
                text = if (isPublic) "Public" else "Private",
                fontSize = 14.sp
            )
        }

        Button(
            onClick = { saved = true },
            enabled = name.isNotBlank() && !saved,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (saved) "Saved" else "Save Profile")
        }
    }
}

// --- Previews ---

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun StateExamplesPreview() {
    AndroidjetpackcomposetutorialTheme {
        StateExamplesScreen()
    }
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun StateExamplesDarkPreview() {
    AndroidjetpackcomposetutorialTheme {
        StateExamplesScreen()
    }
}
