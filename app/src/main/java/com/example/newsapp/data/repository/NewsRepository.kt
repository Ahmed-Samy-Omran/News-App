package com.example.newsapp.data.repository

import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.data.model.SourceResponse

interface NewsRepository {
    suspend fun getNewsSources(apiKey: String, category: String): SourceResponse
    suspend fun getNews(apiKey: String, source: String): NewsResponse
}