package com.example.newsapp.ui.news

import com.example.newsapp.model.NewsResponse
import com.example.newsapp.model.SourceResponse

interface NewsRepository {
    suspend fun getNewsSources(apiKey: String, category: String): SourceResponse
    suspend fun getNews(apiKey: String, source: String): NewsResponse
}