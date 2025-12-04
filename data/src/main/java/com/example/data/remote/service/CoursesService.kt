package com.example.data.remote.service

import com.example.data.remote.model.RemoteCoursesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CoursesService {

    @GET("u/0/uc")
    suspend fun getCourses(
        @Query("id") id: String = "15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q",
        @Query("export") export: String = "download"
    ): RemoteCoursesResponse
}
