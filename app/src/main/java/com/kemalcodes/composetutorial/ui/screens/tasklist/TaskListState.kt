// TaskListState.kt — Holds all the data the task list screen needs to display.
package com.kemalcodes.composetutorial.ui.screens.tasklist

import com.kemalcodes.composetutorial.domain.model.Task
import com.kemalcodes.composetutorial.domain.model.TaskFilter

data class TaskListState(
    val tasks: List<Task> = emptyList(),
    val filter: TaskFilter = TaskFilter.ALL,
    val searchQuery: String = "",
    val isLoading: Boolean = true
)
