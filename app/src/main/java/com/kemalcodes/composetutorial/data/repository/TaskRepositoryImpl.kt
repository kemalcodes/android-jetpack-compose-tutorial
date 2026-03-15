// TaskRepositoryImpl.kt — Concrete implementation of TaskRepository using Room.
package com.kemalcodes.composetutorial.data.repository

import com.kemalcodes.composetutorial.data.local.TaskDao
import com.kemalcodes.composetutorial.data.mapper.toDomainModel
import com.kemalcodes.composetutorial.data.mapper.toEntity
import com.kemalcodes.composetutorial.domain.model.Task
import com.kemalcodes.composetutorial.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override fun getAllTasks(): Flow<List<Task>> =
        taskDao.getAllTasks().map { entities -> entities.map { it.toDomainModel() } }
    override fun getActiveTasks(): Flow<List<Task>> =
        taskDao.getActiveTasks().map { entities -> entities.map { it.toDomainModel() } }
    override fun getCompletedTasks(): Flow<List<Task>> =
        taskDao.getCompletedTasks().map { entities -> entities.map { it.toDomainModel() } }
    override fun searchTasks(query: String): Flow<List<Task>> =
        taskDao.searchTasks(query).map { entities -> entities.map { it.toDomainModel() } }
    override suspend fun insertTask(task: Task) = taskDao.insert(task.toEntity())
    override suspend fun updateTask(task: Task) = taskDao.update(task.toEntity())
    override suspend fun deleteTask(task: Task) = taskDao.delete(task.toEntity())
    override suspend fun toggleTaskCompleted(taskId: Long) = taskDao.toggleCompleted(taskId)
}
