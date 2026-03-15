// TaskRepository.kt — Interface defining task operations.
package com.kemalcodes.composetutorial.domain.repository

import com.kemalcodes.composetutorial.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    fun getActiveTasks(): Flow<List<Task>>
    fun getCompletedTasks(): Flow<List<Task>>
    fun searchTasks(query: String): Flow<List<Task>>
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun toggleTaskCompleted(taskId: Long)
}
