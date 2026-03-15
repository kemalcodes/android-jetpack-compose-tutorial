// AddTaskViewModel.kt — Manages the form state for creating a new task.
// It holds the current values of each form field and handles the save action.
package com.kemalcodes.composetutorial.ui.screens.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemalcodes.composetutorial.domain.model.Category
import com.kemalcodes.composetutorial.domain.model.Priority
import com.kemalcodes.composetutorial.domain.model.Task
import com.kemalcodes.composetutorial.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Holds all the form fields for adding a new task
data class AddTaskState(
    val title: String = "",
    val description: String = "",
    val category: Category = Category.PERSONAL,
    val priority: Priority = Priority.MEDIUM,
    val isSaved: Boolean = false
)

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AddTaskState())
    val state: StateFlow<AddTaskState> = _state.asStateFlow()

    fun updateTitle(title: String) {
        _state.value = _state.value.copy(title = title)
    }

    fun updateDescription(description: String) {
        _state.value = _state.value.copy(description = description)
    }

    fun updateCategory(category: Category) {
        _state.value = _state.value.copy(category = category)
    }

    fun updatePriority(priority: Priority) {
        _state.value = _state.value.copy(priority = priority)
    }

    // Save the task to the database and signal the UI to navigate back
    fun saveTask() {
        val current = _state.value
        if (current.title.isBlank()) return

        viewModelScope.launch {
            repository.insertTask(
                Task(
                    title = current.title.trim(),
                    description = current.description.trim(),
                    category = current.category,
                    priority = current.priority
                )
            )
            _state.value = _state.value.copy(isSaved = true)
        }
    }
}
