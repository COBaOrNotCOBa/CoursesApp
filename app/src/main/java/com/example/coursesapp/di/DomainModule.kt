package com.example.coursesapp.di

import com.example.domain.repository.CoursesRepository
import com.example.domain.usecase.AuthUseCases
import com.example.domain.usecase.CoursesUseCases
import com.example.domain.usecase.GetCoursesUseCase
import com.example.domain.usecase.GetFavoriteCoursesUseCase
import com.example.domain.usecase.SortCoursesByPublishDateUseCase
import com.example.domain.usecase.ToggleFavoriteUseCase
import com.example.domain.usecase.ValidateEmailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideValidateEmailUseCase(): ValidateEmailUseCase {
        return ValidateEmailUseCase()
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(
        validateEmailUseCase: ValidateEmailUseCase
    ): AuthUseCases {
        return AuthUseCases(
            validateEmail = validateEmailUseCase
        )
    }

    @Provides
    @Singleton
    fun provideGetCoursesUseCase(
        coursesRepository: CoursesRepository
    ): GetCoursesUseCase {
        return GetCoursesUseCase(coursesRepository)
    }

    @Provides
    @Singleton
    fun provideGetFavoriteCoursesUseCase(
        coursesRepository: CoursesRepository
    ): GetFavoriteCoursesUseCase {
        return GetFavoriteCoursesUseCase(coursesRepository)
    }

    @Provides
    @Singleton
    fun provideToggleFavoriteUseCase(
        coursesRepository: CoursesRepository
    ): ToggleFavoriteUseCase {
        return ToggleFavoriteUseCase(coursesRepository)
    }

    @Provides
    @Singleton
    fun provideSortCoursesByPublishDateUseCase(): SortCoursesByPublishDateUseCase {
        return SortCoursesByPublishDateUseCase()
    }

    @Provides
    @Singleton
    fun provideCoursesUseCases(
        getCoursesUseCase: GetCoursesUseCase,
        getFavoriteCoursesUseCase: GetFavoriteCoursesUseCase,
        toggleFavoriteUseCase: ToggleFavoriteUseCase,
        sortCoursesByPublishDateUseCase: SortCoursesByPublishDateUseCase
    ): CoursesUseCases {
        return CoursesUseCases(
            getCourses = getCoursesUseCase,
            getFavoriteCourses = getFavoriteCoursesUseCase,
            toggleFavorite = toggleFavoriteUseCase,
            sortCoursesByPublishDate = sortCoursesByPublishDateUseCase
        )
    }
}
