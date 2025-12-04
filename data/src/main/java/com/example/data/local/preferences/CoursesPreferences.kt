package com.example.data.local.preferences

interface CoursesPreferences {

    fun isInitialFavoritesSynced(): Boolean

    fun setInitialFavoritesSynced(value: Boolean)
}
