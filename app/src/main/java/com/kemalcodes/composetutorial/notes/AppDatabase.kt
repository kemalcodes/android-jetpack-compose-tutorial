package com.kemalcodes.composetutorial.notes

// Tutorial #14: Dependency Injection with Hilt — Room Database
// This file defines the Room database class.
// Compare this to Tutorial #13: we no longer need the DatabaseProvider singleton!
// In Tutorial #13, we had a manual singleton with synchronized blocks.
// With Hilt, the AppModule handles creating and providing the database as a singleton.
// This keeps the database class clean and focused on its actual job.

import androidx.room.Database
import androidx.room.RoomDatabase

// @Database tells Room which entities (tables) this database contains
// No singleton pattern needed here — Hilt manages the single instance via AppModule
@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
