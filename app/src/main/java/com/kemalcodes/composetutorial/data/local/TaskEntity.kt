// TaskEntity.kt — The Room database representation of a task.
// Room uses annotations to map this class to a SQLite table.
// Each property becomes a column in the "tasks" table.
package com.kemalcodes.composetutorial.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    // Room auto-generates the ID when inserting a new task
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,

    // We store enums as strings in the database for readability
    val category: String = "PERSONAL",
    val priority: String = "MEDIUM",

    val createdAt: Long = System.currentTimeMillis()
)
