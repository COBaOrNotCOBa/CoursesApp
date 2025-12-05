package com.example.data.repository

import com.example.data.local.database.CoursesDao
import com.example.data.remote.service.CoursesService
import com.example.domain.model.Course
import com.example.domain.repository.CoursesRepository
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val coursesService: CoursesService,
    private val coursesDao: CoursesDao
) : CoursesRepository {

    override suspend fun getCourses(): List<Course> {
        return try {
            val currentEntities = coursesDao.getAllCourses()
            val currentById = currentEntities.associateBy { it.id }

            val remoteResponse = coursesService.getCourses()

            val newEntities = remoteResponse.courses.map { remoteCourse ->
                val id = remoteCourse.id.toString()
                val previousFavorite = currentById[id]?.isFavorite
                remoteCourse.toCourseEntity(previousFavorite)
            }

            coursesDao.upsertCourses(newEntities)

            newEntities.map { it.toDomainCourse() }
        } catch (_: Throwable) {
            val cached = coursesDao.getAllCourses()
            cached.map { it.toDomainCourse() }
        }
    }

    override suspend fun toggleFavorite(courseId: String) {
        val courseEntity = coursesDao.getCourseById(courseId) ?: return
        val newFavorite = !courseEntity.isFavorite
        coursesDao.updateFavorite(courseId = courseId, isFavorite = newFavorite)
    }

    override suspend fun getFavoriteCourses(): List<Course> {
        val favoriteEntities = coursesDao.getFavoriteCourses()
        return favoriteEntities.map { it.toDomainCourse() }
    }
}
