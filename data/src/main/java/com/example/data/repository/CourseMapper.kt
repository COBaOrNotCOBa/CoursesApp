package com.example.data.repository

import com.example.data.remote.model.RemoteCourse
import com.example.domain.model.Course

fun RemoteCourse.toDomainCourse(isFavorite: Boolean): Course {
    val courseId = id.toString()

    val normalizedPrice = price.filter { it.isDigit() }
    val priceInt = normalizedPrice.toIntOrNull() ?: 0

    val rateNormalized = rate.replace(',', '.')
    val rateDouble = rateNormalized.toDoubleOrNull() ?: 0.0

    return Course(
        id = courseId,
        title = title,
        text = text,
        price = priceInt,
        rate = rateDouble,
        startDate = startDate,
        publishDate = publishDate,
        hasLike = isFavorite
    )
}
