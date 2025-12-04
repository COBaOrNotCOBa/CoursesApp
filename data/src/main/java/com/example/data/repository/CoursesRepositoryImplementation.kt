package com.example.data.repository

import com.example.data.local.database.FavoriteCourseDao
import com.example.data.local.database.FavoriteCourseEntity
import com.example.data.local.preferences.CoursesPreferences
import com.example.data.remote.service.CoursesService
import com.example.domain.model.Course
import com.example.domain.repository.CoursesRepository
import javax.inject.Inject

class CoursesRepositoryImplementation @Inject constructor(
    private val coursesService: CoursesService,
    private val favoriteCourseDao: FavoriteCourseDao,
    private val coursesPreferences: CoursesPreferences
) : CoursesRepository {

    override suspend fun getCourses(): List<Course> {
        val favoriteEntities = favoriteCourseDao.getAllFavoriteCourses()
        val favoriteCourseIds = favoriteEntities
            .map { entity -> entity.courseId }
            .toMutableSet()

        val remoteResponse = coursesService.getCourses()

        if (!coursesPreferences.isInitialFavoritesSynced()) {
            remoteResponse.courses.forEach { remoteCourse ->
                if (remoteCourse.hasLike) {
                    val courseId = remoteCourse.id.toString()
                    if (!favoriteCourseIds.contains(courseId)) {
                        favoriteCourseDao.insertFavoriteCourse(
                            FavoriteCourseEntity(courseId = courseId)
                        )
                        favoriteCourseIds.add(courseId)
                    }
                }
            }
            coursesPreferences.setInitialFavoritesSynced(true)
        }

        return remoteResponse.courses.map { remoteCourse ->
            val courseId = remoteCourse.id.toString()
            val isFavorite = favoriteCourseIds.contains(courseId)
            remoteCourse.toDomainCourse(isFavorite = isFavorite)
        }
    }

    override suspend fun toggleFavorite(courseId: String) {
        val existingEntity = favoriteCourseDao.getFavoriteCourseById(courseId)
        if (existingEntity == null) {
            favoriteCourseDao.insertFavoriteCourse(FavoriteCourseEntity(courseId = courseId))
        } else {
            favoriteCourseDao.deleteFavoriteCourse(courseId = courseId)
        }
    }

    override suspend fun getFavoriteCourses(): List<Course> {
        return getCourses().filter { course -> course.hasLike }
    }
}
