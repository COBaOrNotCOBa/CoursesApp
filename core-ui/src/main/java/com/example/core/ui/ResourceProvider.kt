package com.example.core.ui

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceProvider @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    fun getString(resId: Int): String = context.getString(resId)
}

