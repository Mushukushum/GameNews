package com.example.news.data.repository

import com.example.news.data.News
import com.example.news.data.remote.NewsApi
import javax.inject.Inject

class Repository @Inject constructor(
    private val newsApi: NewsApi
) {

    suspend fun getNews(page: Int): List<News>{
        return newsApi.getNews(page)
    }

}