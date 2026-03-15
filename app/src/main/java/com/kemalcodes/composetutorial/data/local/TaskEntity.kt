// TaskEntity.kt — Room database entity for tasks.
package com.kemalcodes.composetutorial.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val category: String = "PERSONAL",
    val priority: String = "MEDIUM",
    val createdAt: Long = System.currentTimeMillis()
)
