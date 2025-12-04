package com.example.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCoursesResponse(
    @SerialName("courses")
    val courses: List<RemoteCourse>
)
