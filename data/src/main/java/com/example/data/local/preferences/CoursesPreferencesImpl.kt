package com.example.data.local.preferences

import android.content.SharedPreferences
import javax.inject.Inject
import androidx.core.content.edit

class CoursesPreferencesImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : CoursesPreferences {

    override fun isInitialFavoritesSynced(): Boolean {
        return sharedPreferences.getBoolean(KEY_INITIAL_FAVORITES_SYNCED, false)
    }

    override fun setInitialFavoritesSynced(value: Boolean) {
        sharedPreferences.edit {
            putBoolean(KEY_INITIAL_FAVORITES_SYNCED, value)
        }
    }

    private companion object {
        private const val KEY_INITIAL_FAVORITES_SYNCED = "initial_favorites_synced"
    }
}
