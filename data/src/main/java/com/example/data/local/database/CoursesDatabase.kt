package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CourseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CoursesDatabase : RoomDatabase() {

    abstract fun coursesDao(): CoursesDao
}
