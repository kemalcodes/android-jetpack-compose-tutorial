package com.kemalcodes.composetutorial.di

// Tutorial #14: Dependency Injection with Hilt — Hilt Module
// This file demonstrates @Module and @InstallIn annotations.
// A Hilt module tells Hilt HOW to create instances of certain types.
// @InstallIn(SingletonComponent::class) means these dependencies live as long as the app.
//
// This replaces the manual DatabaseProvider singleton from Tutorial #13.
// Instead of writing synchronized blocks and volatile variables,
// we just annotate methods with @Provides and @Singleton — Hilt does the rest.

import android.content.Context
import androidx.room.Room
import com.kemalcodes.composetutorial.notes.AppDatabase
import com.kemalcodes.composetutorial.notes.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// @Module tells Hilt this class contains dependency providers
// @InstallIn(SingletonComponent::class) means these live for the entire app lifecycle
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // @Provides tells Hilt how to create an AppDatabase instance
    // @Singleton ensures only one database instance exists (same as DatabaseProvider did manually)
    // @ApplicationContext is a Hilt qualifier that gives us the app context safely
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "notes_db"
        ).build()
    }

    // @Provides tells Hilt how to get a NoteDao from the database
    // Hilt sees that provideDatabase() returns AppDatabase, so it calls that first,
    // then passes the result here — all the wiring is automatic
    @Provides
    @Singleton
    fun provideNoteDao(database: AppDatabase): NoteDao {
        return database.noteDao()
    }
}
