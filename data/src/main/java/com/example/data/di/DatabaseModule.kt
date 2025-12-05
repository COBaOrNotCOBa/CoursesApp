package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.database.CoursesDao
import com.example.data.local.database.CoursesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCoursesDatabase(
        @ApplicationContext context: Context
    ): CoursesDatabase {
        return Room.databaseBuilder(
            context,
            CoursesDatabase::class.java,
            "courses_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCoursesDao(
        database: CoursesDatabase
    ): CoursesDao = database.coursesDao()
}
