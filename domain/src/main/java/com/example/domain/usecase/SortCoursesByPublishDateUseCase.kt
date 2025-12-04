package com.example.domain.usecase

import com.example.domain.model.Course

class SortCoursesByPublishDateUseCase {

    operator fun invoke(courses: List<Course>): List<Course> {
        return courses.sortedByDescending { it.publishDate }
    }
}
