package com.example.news.data.remote

import com.example.news.data.News
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/")
    suspend fun getNews(
        @Query("page") page: Int
    ): List<News>

}