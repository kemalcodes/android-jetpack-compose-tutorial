// TaskListViewModel.kt — Manages state for the task list screen using MVI pattern.
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
        loadTasks()
    }

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
                viewModelScope.launch { repository.toggleTaskCompleted(intent.taskId) }
            }
            is TaskListIntent.DeleteTask -> {
                viewModelScope.launch { repository.deleteTask(intent.task) }
            }
        }
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val query = _state.value.searchQuery
            val tasksFlow = if (query.isNotBlank()) {
                repository.searchTasks(query)
            } else {
                when (_state.value.filter) {
                    TaskFilter.ALL -> repository.getAllTasks()
                    TaskFilter.ACTIVE -> repository.getActiveTasks()
                    TaskFilter.COMPLETED -> repository.getCompletedTasks()
                }
            }
            tasksFlow.collectLatest { tasks ->
                _state.value = _state.value.copy(tasks = tasks, isLoading = false)
            }
        }
    }
}
