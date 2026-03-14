package com.kemalcodes.composetutorial

// Tutorial #11: Side Effects — LaunchedEffect and Friends
// https://kemalcodes.com/posts/jetpack-compose-tutorial-side-effects/
//
// This file demonstrates:
// - LaunchedEffect — run code when a key changes
// - rememberCoroutineScope — launch coroutines from events
// - derivedStateOf — computed state from other state
// - A timer app that uses LaunchedEffect for the countdown
// - A filtered list that uses derivedStateOf

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kemalcodes.composetutorial.ui.theme.AndroidjetpackcomposetutorialTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidjetpackcomposetutorialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SideEffectsScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// --- Main Screen ---

@Composable
fun SideEffectsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Side Effects", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        // Timer using LaunchedEffect
        TimerSection()

        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

        // Filtered list using derivedStateOf + rememberCoroutineScope
        FilteredListSection()
    }
}

// --- Timer Section ---
// Uses LaunchedEffect(isRunning) — starts/stops automatically when key changes

@Composable
fun TimerSection() {
    var seconds by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }

    // LaunchedEffect runs when isRunning changes
    // When isRunning becomes false, the old coroutine is cancelled
    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (isActive) {  // isActive checks if coroutine is still alive
                delay(1000)
                seconds++
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Timer",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Display time as MM:SS
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        Text(
            text = "%02d:%02d".format(minutes, remainingSeconds),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { isRunning = !isRunning }) {
                Text(if (isRunning) "Pause" else "Start")
            }
            OutlinedButton(onClick = { isRunning = false; seconds = 0 }) {
                Text("Reset")
            }
        }

        Text(
            text = if (isRunning) "Running..." else "Stopped",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// --- Filtered List Section ---
// Uses derivedStateOf for efficient filtering
// Uses rememberCoroutineScope for scroll-to-top button

@Composable
fun FilteredListSection() {
    var searchQuery by remember { mutableStateOf("") }
    val allItems = remember {
        listOf(
            "Kotlin", "Java", "Python", "JavaScript", "TypeScript",
            "Rust", "Go", "Swift", "Dart", "C++", "Ruby", "PHP"
        )
    }

    // derivedStateOf — only recalculates when searchQuery or allItems changes
    // This prevents unnecessary recompositions
    val filteredItems by remember {
        derivedStateOf {
            if (searchQuery.isEmpty()) allItems
            else allItems.filter { it.contains(searchQuery, ignoreCase = true) }
        }
    }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Show "scroll to top" only when scrolled past first item
    val showScrollToTop by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    Column {
        Text(
            text = "Filtered List",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search languages") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${filteredItems.size} of ${allItems.size} languages",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Scroll to top button — uses rememberCoroutineScope
        // because it's triggered by a user click, not by state change
        if (showScrollToTop) {
            OutlinedButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                }
            ) {
                Text("Scroll to Top")
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f)
        ) {
            items(filteredItems) { item ->
                Text(
                    text = item,
                    modifier = Modifier.padding(vertical = 12.dp),
                    fontSize = 16.sp
                )
            }
        }
    }
}

// --- Previews ---

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun SideEffectsPreview() {
    AndroidjetpackcomposetutorialTheme {
        SideEffectsScreen()
    }
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SideEffectsDarkPreview() {
    AndroidjetpackcomposetutorialTheme {
        SideEffectsScreen()
    }
}
