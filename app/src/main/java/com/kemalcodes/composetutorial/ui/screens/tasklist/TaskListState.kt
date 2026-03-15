// TaskListState.kt — Holds all the data the task list screen needs to display.
// The ViewModel updates this state, and the UI simply renders whatever is here.
// This makes the screen predictable: same state always produces the same UI.
package com.kemalcodes.composetutorial.ui.screens.tasklist

import com.kemalcodes.composetutorial.domain.model.Task
import com.kemalcodes.composetutorial.domain.model.TaskFilter

data class TaskListState(
    // The list of tasks currently visible (filtered and/or searched)
    val tasks: List<Task> = emptyList(),

    // Which filter is currently active (ALL, ACTIVE, or COMPLETED)
    val filter: TaskFilter = TaskFilter.ALL,

    // The current search text typed by the user
    val searchQuery: String = "",

    // Whether tasks are still loading from the database
    val isLoading: Boolean = true
)
