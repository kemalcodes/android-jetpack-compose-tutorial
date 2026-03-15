// TaskMapper.kt — Converts between the database entity and the domain model.
// This separation means our domain layer never depends on Room,
// and we can change the database schema without affecting the rest of the app.
package com.kemalcodes.composetutorial.data.mapper

import com.kemalcodes.composetutorial.data.local.TaskEntity
import com.kemalcodes.composetutorial.domain.model.Category
import com.kemalcodes.composetutorial.domain.model.Priority
import com.kemalcodes.composetutorial.domain.model.Task

// Convert a database entity to a domain model
fun TaskEntity.toDomainModel(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        // Parse the stored string back to the enum, with a safe default
        category = try { Category.valueOf(category) } catch (e: Exception) { Category.PERSONAL },
        priority = try { Priority.valueOf(priority) } catch (e: Exception) { Priority.MEDIUM },
        createdAt = createdAt
    )
}

// Convert a domain model to a database entity
fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        // Store enums as their name strings
        category = category.name,
        priority = priority.name,
        createdAt = createdAt
    )
}
