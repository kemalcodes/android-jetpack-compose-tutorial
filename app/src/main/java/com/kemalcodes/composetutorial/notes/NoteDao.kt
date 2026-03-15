package com.kemalcodes.composetutorial.notes

// Tutorial #14: Dependency Injection with Hilt — Room DAO
// This file defines the Data Access Object (DAO) for notes.
// The DAO provides methods to interact with the database.
// With Hilt, this DAO gets provided automatically via the AppModule —
// no need to manually create it from the database instance.

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// @Dao tells Room this interface defines database operations
@Dao
interface NoteDao {
    // Flow means the UI updates automatically when data changes
    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Insert
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    // Search notes by title — returns a Flow for reactive updates
    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchNotes(query: String): Flow<List<Note>>
}
