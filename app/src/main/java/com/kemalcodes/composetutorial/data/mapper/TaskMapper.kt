// TaskMapper.kt — Converts between database entities and domain models.
package com.kemalcodes.composetutorial.data.mapper

import com.kemalcodes.composetutorial.data.local.TaskEntity
import com.kemalcodes.composetutorial.domain.model.Category
import com.kemalcodes.composetutorial.domain.model.Priority
import com.kemalcodes.composetutorial.domain.model.Task

fun TaskEntity.toDomainModel(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        category = try { Category.valueOf(category) } catch (e: Exception) { Category.PERSONAL },
        priority = try { Priority.valueOf(priority) } catch (e: Exception) { Priority.MEDIUM },
        createdAt = createdAt
    )
}

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        category = category.name,
        priority = priority.name,
        createdAt = createdAt
    )
}
