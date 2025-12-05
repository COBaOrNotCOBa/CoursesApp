package com.example.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.CoursesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val coursesUseCases: CoursesUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState(isLoading = true))
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                val courses = coursesUseCases.getCourses()
                val sortedCourses =
                    if (_uiState.value.isSortedByPublishDate) {
                        coursesUseCases.sortCoursesByPublishDate(courses)
                    } else {
                        courses
                    }

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        courses = sortedCourses,
                        errorMessage = null
                    )
                }
            } catch (throwable: Throwable) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = throwable.message ?: "Ошибка загрузки курсов"
                    )
                }
            }
        }
    }

    fun onSortByPublishDateClicked() {
        _uiState.update { currentState ->
            currentState.copy(
                courses = coursesUseCases.sortCoursesByPublishDate(currentState.courses)
            )
        }
    }

    fun onFavoriteCourseClicked(courseId: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val updatedCourses = currentState.courses.map { course ->
                    if (course.id == courseId) {
                        course.copy(hasLike = !course.hasLike)
                    } else {
                        course
                    }
                }
                currentState.copy(courses = updatedCourses)
            }

            try {
                coursesUseCases.toggleFavorite(courseId)
            } catch (_: Throwable) {
            }
        }
    }
}
