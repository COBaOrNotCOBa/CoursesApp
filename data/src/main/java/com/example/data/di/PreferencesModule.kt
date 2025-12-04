package com.example.data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.data.local.preferences.CoursesPreferences
import com.example.data.local.preferences.CoursesPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            COURSES_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideCoursesPreferences(
        sharedPreferences: SharedPreferences
    ): CoursesPreferences {
        return CoursesPreferencesImpl(sharedPreferences)
    }
}

private const val COURSES_PREFERENCES_NAME = "courses_preferences"
