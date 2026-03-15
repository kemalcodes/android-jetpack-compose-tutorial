// TaskRepository.kt — Interface defining what operations the app can do with tasks.
// This is in the domain layer, so it has no knowledge of Room or any other framework.
// The actual implementation lives in the data layer.
package com.kemalcodes.composetutorial.domain.repository

import com.kemalcodes.composetutorial.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    // Observe all tasks as a stream (updates automatically when data changes)
    fun getAllTasks(): Flow<List<Task>>

    // Observe only active (incomplete) tasks
    fun getActiveTasks(): Flow<List<Task>>

    // Observe only completed tasks
    fun getCompletedTasks(): Flow<List<Task>>

    // Search tasks by title
    fun searchTasks(query: String): Flow<List<Task>>

    // Add a new task to the database
    suspend fun insertTask(task: Task)

    // Update an existing task
    suspend fun updateTask(task: Task)

    // Remove a task from the database
    suspend fun deleteTask(task: Task)

    // Toggle a task between completed and active
    suspend fun toggleTaskCompleted(taskId: Long)
}
