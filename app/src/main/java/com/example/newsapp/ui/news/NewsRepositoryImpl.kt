package com.example.newsapp.ui.news

import com.example.newsapp.api.ApiServices
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.model.SourceResponse

class NewsRepositoryImpl(private val apiServices: ApiServices):NewsRepository {
    override suspend fun getNewsSources(apiKey: String, category: String): SourceResponse {
        return apiServices.getNewsSources(apiKey, category)
    }

    override suspend fun getNews(apiKey: String, source: String): NewsResponse {
        return apiServices.getNews(apiKey, source)
    }

}