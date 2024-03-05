package com.example.newsapp.api

import com.example.newsapp.model.NewsResponse
import com.example.newsapp.model.SourceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import javax.xml.transform.Source

interface Services {
    @GET("v2/top-headlines/sources")
    fun getNewsSources( //this method when i call it return the sources of news
        @Query("apiKey")apiKey:String,
        @Query("category")category:String
    ):Call<SourceResponse>

    @GET("v2/everything")
    fun getNews(
        @Query("apiKey")apiKey:String,
      @Query ("sources") source:String):Call<NewsResponse>
}