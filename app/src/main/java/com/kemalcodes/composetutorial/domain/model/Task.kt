// Task.kt — The core domain model representing a task in our app.
// This is a plain data class with no database or framework dependencies,
// keeping our domain layer clean and testable.
package com.kemalcodes.composetutorial.domain.model

data class Task(
    // Unique identifier for each task (0 means "not yet saved")
    val id: Long = 0,

    // The main text of the task, like "Buy groceries"
    val title: String,

    // Optional extra details about the task
    val description: String = "",

    // Whether the task has been completed
    val isCompleted: Boolean = false,

    // Which category this task belongs to (e.g., WORK, PERSONAL)
    val category: Category = Category.PERSONAL,

    // How urgent this task is
    val priority: Priority = Priority.MEDIUM,

    // When the task was created (milliseconds since epoch)
    val createdAt: Long = System.currentTimeMillis()
)
