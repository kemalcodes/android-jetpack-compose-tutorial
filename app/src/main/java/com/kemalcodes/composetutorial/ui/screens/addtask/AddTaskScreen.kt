// AddTaskScreen.kt — Form for creating a new task with title, description,
// category and priority selection chips, and a save button.
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

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) onTaskSaved()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("New Task", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = state.title,
            onValueChange = { viewModel.updateTitle(it) },
            label = { Text("Title") },
            placeholder = { Text("What needs to be done?") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = state.description,
            onValueChange = { viewModel.updateDescription(it) },
            label = { Text("Description") },
            placeholder = { Text("Add some details...") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Text("Category", style = MaterialTheme.typography.titleSmall)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Category.entries.forEach { category ->
                FilterChip(
                    selected = state.category == category,
                    onClick = { viewModel.updateCategory(category) },
                    label = { Text(category.name.lowercase().replaceFirstChar { it.uppercase() }) }
                )
            }
        }

        Text("Priority", style = MaterialTheme.typography.titleSmall)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Priority.entries.forEach { priority ->
                FilterChip(
                    selected = state.priority == priority,
                    onClick = { viewModel.updatePriority(priority) },
                    label = { Text(priority.name.lowercase().replaceFirstChar { it.uppercase() }) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.saveTask() },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.title.isNotBlank()
        ) {
            Text("Save Task")
        }
    }
}
