package com.kemalcodes.composetutorial.notes

// Tutorial #14: Dependency Injection with Hilt — Notes Screen
// This file shows the UI for the notes app.
// The key difference from Tutorial #13: we use hiltViewModel() instead of viewModel().
// hiltViewModel() tells Compose to get the ViewModel from Hilt's dependency graph,
// which means the ViewModel receives its injected dependencies automatically.

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

// Note: We use viewModel() here which works with @HiltViewModel when the Activity
// is annotated with @AndroidEntryPoint. Hilt intercepts the ViewModel creation
// and provides all the injected dependencies automatically.
@Composable
fun NotesScreen(modifier: Modifier = Modifier, viewModel: NotesViewModel = viewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(modifier = modifier, floatingActionButton = {
        FloatingActionButton(onClick = { showAddDialog = true }) {
            Icon(Icons.Default.Add, contentDescription = "Add note")
        }
    }) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            // App title
            Text(
                "Notes",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )

            // Search bar to filter notes
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Search notes") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            // Show empty state or notes list
            if (state.notes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        if (state.searchQuery.isEmpty()) "No notes yet. Tap + to add one."
                        else "No notes found.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.notes, key = { it.id }) { note ->
                        // Each note card
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(note.title, fontWeight = FontWeight.Bold)
                                    if (note.content.isNotEmpty()) {
                                        Text(
                                            note.content,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                                IconButton(onClick = { viewModel.deleteNote(note) }) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                    // Notes count at the bottom
                    item {
                        Text(
                            "${state.notes.size} notes",
                            modifier = Modifier.padding(vertical = 16.dp),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }

    // Dialog to add a new note
    if (showAddDialog) {
        var title by remember { mutableStateOf("") }
        var content by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("New Note") },
            text = {
                Column {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text("Content") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.addNote(title, content); showAddDialog = false },
                    enabled = title.isNotBlank()
                ) { Text("Save") }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) { Text("Cancel") }
            }
        )
    }
}
