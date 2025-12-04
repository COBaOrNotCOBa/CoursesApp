package com.example.domain.model

data class Course(
    val id: String,
    val title: String,
    val text: String,
    val price: Int,
    val rate: Double,
    val startDate: String,
    val publishDate: String,
    val hasLike: Boolean
)
