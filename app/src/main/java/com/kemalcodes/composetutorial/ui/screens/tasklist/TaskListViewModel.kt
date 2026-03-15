// TaskListViewModel.kt — Manages the state for the task list screen.
// It listens to the repository for task changes and processes user intents.
// The MVI pattern keeps the data flow one-directional: Intent -> ViewModel -> State -> UI.
package com.kemalcodes.composetutorial.ui.screens.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemalcodes.composetutorial.domain.model.TaskFilter
import com.kemalcodes.composetutorial.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TaskListState())
    val state: StateFlow<TaskListState> = _state.asStateFlow()

    init {
        // Start observing tasks as soon as the ViewModel is created
        loadTasks()
    }

    // Process each user action
    fun onIntent(intent: TaskListIntent) {
        when (intent) {
            is TaskListIntent.SetFilter -> {
                _state.value = _state.value.copy(filter = intent.filter)
                loadTasks()
            }
            is TaskListIntent.Search -> {
                _state.value = _state.value.copy(searchQuery = intent.query)
                loadTasks()
            }
            is TaskListIntent.ToggleTask -> {
                viewModelScope.launch {
                    repository.toggleTaskCompleted(intent.taskId)
                }
            }
            is TaskListIntent.DeleteTask -> {
                viewModelScope.launch {
                    repository.deleteTask(intent.task)
                }
            }
        }
    }

    // Load tasks based on the current filter and search query
    private fun loadTasks() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val query = _state.value.searchQuery
            val tasksFlow = if (query.isNotBlank()) {
                // If user is searching, use the search query
                repository.searchTasks(query)
            } else {
                // Otherwise, use the selected filter
                when (_state.value.filter) {
                    TaskFilter.ALL -> repository.getAllTasks()
                    TaskFilter.ACTIVE -> repository.getActiveTasks()
                    TaskFilter.COMPLETED -> repository.getCompletedTasks()
                }
            }

            // collectLatest cancels the previous collection if a new one starts
            tasksFlow.collectLatest { tasks ->
                _state.value = _state.value.copy(
                    tasks = tasks,
                    isLoading = false
                )
            }
        }
    }
}
