// AppModule.kt — Hilt module that tells the dependency injection framework
// how to create the objects our app needs. It provides the database, DAO,
// and binds the repository interface to its implementation.
package com.kemalcodes.composetutorial.di

import android.content.Context
import androidx.room.Room
import com.kemalcodes.composetutorial.data.local.AppDatabase
import com.kemalcodes.composetutorial.data.local.TaskDao
import com.kemalcodes.composetutorial.data.repository.TaskRepositoryImpl
import com.kemalcodes.composetutorial.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    // Binds tells Hilt: "when someone asks for TaskRepository, give them TaskRepositoryImpl"
    @Binds
    @Singleton
    abstract fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository

    // Companion object holds @Provides methods for things Hilt cannot create automatically
    companion object {

        // Create a single database instance for the entire app
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "task_manager_db"
            ).build()
        }

        // Extract the DAO from the database so it can be injected directly
        @Provides
        @Singleton
        fun provideTaskDao(database: AppDatabase): TaskDao {
            return database.taskDao()
        }
    }
}
