package com.example.newsapp.data.api

import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.data.model.SourceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("v2/top-headlines/sources")
    suspend fun getNewsSources( //this method when i call it return the sources of news
        @Query("apiKey") apiKey: String,
        @Query("category") category: String
    ): SourceResponse

    @GET("v2/everything")
    suspend fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("sources") source: String
    ): NewsResponse
}