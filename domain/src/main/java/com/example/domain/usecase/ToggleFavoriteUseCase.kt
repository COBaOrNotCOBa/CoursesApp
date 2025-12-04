package com.example.domain.usecase

import com.example.domain.repository.CoursesRepository

class ToggleFavoriteUseCase(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(courseId: String) {
        repository.toggleFavorite(courseId)
    }
}
