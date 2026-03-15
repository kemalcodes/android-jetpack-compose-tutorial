// TaskListIntent.kt — User actions on the task list screen.
package com.kemalcodes.composetutorial.ui.screens.tasklist

import com.kemalcodes.composetutorial.domain.model.Task
import com.kemalcodes.composetutorial.domain.model.TaskFilter

sealed interface TaskListIntent {
    data class SetFilter(val filter: TaskFilter) : TaskListIntent
    data class Search(val query: String) : TaskListIntent
    data class ToggleTask(val taskId: Long) : TaskListIntent
    data class DeleteTask(val task: Task) : TaskListIntent
}
