package com.kemalcodes.composetutorial.domain.model
// Clean domain model — no database annotations
data class Task(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val category: Category = Category.PERSONAL,
    val priority: Priority = Priority.MEDIUM,
    val isCompleted: Boolean = false,
    val dueDate: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
