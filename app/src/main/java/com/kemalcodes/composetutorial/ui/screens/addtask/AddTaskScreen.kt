// AddTaskScreen.kt — Form screen for creating a new task.
// Users can enter a title, description, pick a category and priority,
// then tap Save to add it to the database.
package com.kemalcodes.composetutorial.ui.screens.addtask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kemalcodes.composetutorial.domain.model.Category
import com.kemalcodes.composetutorial.domain.model.Priority

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddTaskScreen(
    onTaskSaved: () -> Unit,
    viewModel: AddTaskViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // When the task is saved, navigate back to the task list
    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onTaskSaved()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "New Task",
            style = MaterialTheme.typography.headlineMedium
        )

        // Title field (required)
        OutlinedTextField(
            value = state.title,
            onValueChange = { viewModel.updateTitle(it) },
            label = { Text("Title") },
            placeholder = { Text("What needs to be done?") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Description field (optional)
        OutlinedTextField(
            value = state.description,
            onValueChange = { viewModel.updateDescription(it) },
            label = { Text("Description") },
            placeholder = { Text("Add some details...") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        // Category selection using filter chips
        Text(
            text = "Category",
            style = MaterialTheme.typography.titleSmall
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Category.entries.forEach { category ->
                FilterChip(
                    selected = state.category == category,
                    onClick = { viewModel.updateCategory(category) },
                    label = {
                        Text(
                            category.name.lowercase()
                                .replaceFirstChar { it.uppercase() }
                        )
                    }
                )
            }
        }

        // Priority selection using filter chips
        Text(
            text = "Priority",
            style = MaterialTheme.typography.titleSmall
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Priority.entries.forEach { priority ->
                FilterChip(
                    selected = state.priority == priority,
                    onClick = { viewModel.updatePriority(priority) },
                    label = {
                        Text(
                            priority.name.lowercase()
                                .replaceFirstChar { it.uppercase() }
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Save button — disabled if title is empty
        Button(
            onClick = { viewModel.saveTask() },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.title.isNotBlank()
        ) {
            Text("Save Task")
        }
    }
}
