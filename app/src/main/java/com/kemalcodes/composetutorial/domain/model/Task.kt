// Task.kt — The core domain model representing a task.
package com.kemalcodes.composetutorial.domain.model

data class Task(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val category: Category = Category.PERSONAL,
    val priority: Priority = Priority.MEDIUM,
    val createdAt: Long = System.currentTimeMillis()
)
