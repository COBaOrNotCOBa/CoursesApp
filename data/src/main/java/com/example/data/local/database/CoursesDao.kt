package com.example.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoursesDao {

    @Query("SELECT * FROM courses")
    suspend fun getAllCourses(): List<CourseEntity>

    @Query("SELECT * FROM courses WHERE isFavorite = 1")
    suspend fun getFavoriteCourses(): List<CourseEntity>

    @Query("SELECT * FROM courses WHERE id = :courseId LIMIT 1")
    suspend fun getCourseById(courseId: String): CourseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCourses(courses: List<CourseEntity>)

    @Query("UPDATE courses SET isFavorite = :isFavorite WHERE id = :courseId")
    suspend fun updateFavorite(courseId: String, isFavorite: Boolean)
}
