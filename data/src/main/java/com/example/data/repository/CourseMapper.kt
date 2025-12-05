package com.example.data.repository

import com.example.data.local.database.CourseEntity
import com.example.data.remote.model.RemoteCourse
import com.example.domain.model.Course

fun RemoteCourse.toCourseEntity(previousFavorite: Boolean?): CourseEntity {
    val normalizedPrice = price.filter { it.isDigit() }
    val priceInt = normalizedPrice.toIntOrNull() ?: 0

    val rateNormalized = rate.replace(',', '.')
    val rateDouble = rateNormalized.toDoubleOrNull() ?: 0.0

    val courseId = id.toString()

    val isFavorite = previousFavorite ?: hasLike

    return CourseEntity(
        id = courseId,
        title = title,
        text = text,
        price = priceInt,
        rate = rateDouble,
        startDate = startDate,
        publishDate = publishDate,
        isFavorite = isFavorite
    )
}

fun CourseEntity.toDomainCourse(): Course = Course(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate,
    publishDate = publishDate,
    hasLike = isFavorite
)
