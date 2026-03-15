// TaskRepositoryImpl.kt — The concrete implementation of TaskRepository.
// It uses the Room DAO to perform database operations and the mapper
// to convert between database entities and domain models.
package com.kemalcodes.composetutorial.data.repository

import com.kemalcodes.composetutorial.data.local.TaskDao
import com.kemalcodes.composetutorial.data.mapper.toDomainModel
import com.kemalcodes.composetutorial.data.mapper.toEntity
import com.kemalcodes.composetutorial.domain.model.Task
import com.kemalcodes.composetutorial.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Hilt injects the TaskDao automatically through the constructor
class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> {
        // Convert each list of entities to a list of domain models
        return taskDao.getAllTasks().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getActiveTasks(): Flow<List<Task>> {
        return taskDao.getActiveTasks().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getCompletedTasks(): Flow<List<Task>> {
        return taskDao.getCompletedTasks().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun searchTasks(query: String): Flow<List<Task>> {
        return taskDao.searchTasks(query).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insert(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.update(task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.delete(task.toEntity())
    }

    override suspend fun toggleTaskCompleted(taskId: Long) {
        taskDao.toggleCompleted(taskId)
    }
}
