package com.example.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteCourseDao {

    @Query("SELECT * FROM favorite_courses")
    suspend fun getAllFavoriteCourses(): List<FavoriteCourseEntity>

    @Query("SELECT * FROM favorite_courses WHERE courseId = :courseId LIMIT 1")
    suspend fun getFavoriteCourseById(courseId: String): FavoriteCourseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCourse(entity: FavoriteCourseEntity)

    @Query("DELETE FROM favorite_courses WHERE courseId = :courseId")
    suspend fun deleteFavoriteCourse(courseId: String)
}
