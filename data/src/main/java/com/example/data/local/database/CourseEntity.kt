package com.example.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey val id: String,
    val title: String,
    val text: String,
    val price: Int,
    val rate: Double,
    val startDate: String,
    val publishDate: String,
    val isFavorite: Boolean
)
