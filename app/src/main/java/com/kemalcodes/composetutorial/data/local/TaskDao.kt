// TaskDao.kt — Data Access Object that defines how we interact with the tasks table.
// Room generates the SQL implementation for each method at compile time.
// Methods returning Flow will automatically emit new data when the table changes.
package com.kemalcodes.composetutorial.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    // Get all tasks, newest first
    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    // Get only tasks that are not yet completed
    @Query("SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY createdAt DESC")
    fun getActiveTasks(): Flow<List<TaskEntity>>

    // Get only tasks that have been completed
    @Query("SELECT * FROM tasks WHERE isCompleted = 1 ORDER BY createdAt DESC")
    fun getCompletedTasks(): Flow<List<TaskEntity>>

    // Search tasks by title (case-insensitive using LIKE)
    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchTasks(query: String): Flow<List<TaskEntity>>

    // Insert a new task (replace if there is a conflict on the primary key)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskEntity)

    // Update an existing task
    @Update
    suspend fun update(task: TaskEntity)

    // Delete a task
    @Delete
    suspend fun delete(task: TaskEntity)

    // Toggle the completed status of a task by its ID
    @Query("UPDATE tasks SET isCompleted = NOT isCompleted WHERE id = :taskId")
    suspend fun toggleCompleted(taskId: Long)
}
