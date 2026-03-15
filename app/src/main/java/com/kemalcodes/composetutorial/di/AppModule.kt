// AppModule.kt — Hilt module providing database, DAO, and repository bindings.
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

    @Binds
    @Singleton
    abstract fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "task_manager_db"
            ).build()
        }

        @Provides
        @Singleton
        fun provideTaskDao(database: AppDatabase): TaskDao {
            return database.taskDao()
        }
    }
}
