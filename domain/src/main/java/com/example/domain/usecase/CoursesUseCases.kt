package com.example.domain.usecase

data class CoursesUseCases(
    val getCourses: GetCoursesUseCase,
    val getFavoriteCourses: GetFavoriteCoursesUseCase,
    val toggleFavorite: ToggleFavoriteUseCase,
    val sortCoursesByPublishDate: SortCoursesByPublishDateUseCase
)
