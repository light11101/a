package com.student.myapplication.labs.commentsapi.network

import com.student.myapplication.labs.commentsapi.data.Comment
import retrofit2.http.GET

interface ApiService {
    @GET("comments")
    suspend fun getComments(): List<Comment>
}