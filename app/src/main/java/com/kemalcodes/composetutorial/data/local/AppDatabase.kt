// AppDatabase.kt — The Room database definition for our app.
// This abstract class tells Room which entities (tables) exist
// and provides access to the DAO for performing queries.
package com.kemalcodes.composetutorial.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // Room generates the implementation of this method automatically
    abstract fun taskDao(): TaskDao
}
