package com.example.presentation.main

import com.example.domain.model.Course

data class MainUiState(
    val isLoading: Boolean = false,
    val courses: List<Course> = emptyList(),
    val errorMessage: String? = null,
    val isSortedByPublishDate: Boolean = false
)
