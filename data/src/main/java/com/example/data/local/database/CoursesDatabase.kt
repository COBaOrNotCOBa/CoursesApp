package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteCourseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CoursesDatabase : RoomDatabase() {

    abstract fun favoriteCourseDao(): FavoriteCourseDao
}
