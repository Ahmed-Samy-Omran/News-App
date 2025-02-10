package com.example.newsapp.data.repository

import com.example.newsapp.data.api.ApiServices
import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.data.model.SourceResponse
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor (private val apiServices: ApiServices): NewsRepository {
    override suspend fun getNewsSources(apiKey: String, category: String): SourceResponse {
        return apiServices.getNewsSources(apiKey, category)
    }

    override suspend fun getNews(apiKey: String, source: String): NewsResponse {
        return apiServices.getNews(apiKey, source)
    }

}