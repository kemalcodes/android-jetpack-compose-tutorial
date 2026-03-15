// TaskListScreen.kt — Main screen showing tasks with search, filters, animations,
// swipe-to-delete, loading skeletons, and an empty state message.
package com.kemalcodes.composetutorial.ui.screens.tasklist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kemalcodes.composetutorial.domain.model.TaskFilter
import com.kemalcodes.composetutorial.ui.components.EmptyState
import com.kemalcodes.composetutorial.ui.components.LoadingSkeleton
import com.kemalcodes.composetutorial.ui.components.SwipeableTaskCard

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Search bar
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { viewModel.onIntent(TaskListIntent.Search(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            placeholder = { Text("Search tasks...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            singleLine = true
        )

        // Filter chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TaskFilter.entries.forEach { filter ->
                FilterChip(
                    selected = state.filter == filter,
                    onClick = { viewModel.onIntent(TaskListIntent.SetFilter(filter)) },
                    label = {
                        Text(filter.name.lowercase().replaceFirstChar { it.uppercase() })
                    }
                )
            }
        }

        // Show loading skeleton, empty state, or task list
        when {
            state.isLoading -> {
                // Show placeholder cards while loading
                LoadingSkeleton()
            }
            state.tasks.isEmpty() -> {
                // Show a helpful message when there are no tasks
                EmptyState(
                    message = if (state.searchQuery.isNotBlank()) {
                        "No tasks match your search."
                    } else {
                        "No tasks yet. Tap the Add tab to create one!"
                    }
                )
            }
            else -> {
                // The actual task list with swipe-to-delete and item animations
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = state.tasks,
                        key = { task -> task.id }
                    ) { task ->
                        // animateItem makes items slide smoothly when added/removed
                        SwipeableTaskCard(
                            task = task,
                            onToggle = { viewModel.onIntent(TaskListIntent.ToggleTask(task.id)) },
                            onDelete = { viewModel.onIntent(TaskListIntent.DeleteTask(task)) },
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            }
        }
    }
}
