// TaskListIntent.kt — Represents every action the user can take on the task list screen.
// Using a sealed interface means we have a fixed set of possible actions,
// and the compiler will warn us if we forget to handle one.
package com.kemalcodes.composetutorial.ui.screens.tasklist

import com.kemalcodes.composetutorial.domain.model.Task
import com.kemalcodes.composetutorial.domain.model.TaskFilter

sealed interface TaskListIntent {
    // User changed the filter (ALL, ACTIVE, COMPLETED)
    data class SetFilter(val filter: TaskFilter) : TaskListIntent

    // User typed in the search bar
    data class Search(val query: String) : TaskListIntent

    // User tapped the checkbox to toggle a task
    data class ToggleTask(val taskId: Long) : TaskListIntent

    // User deleted a task
    data class DeleteTask(val task: Task) : TaskListIntent
}
