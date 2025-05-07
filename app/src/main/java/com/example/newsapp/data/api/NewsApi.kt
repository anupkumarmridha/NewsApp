package com.example.newsapp.data.api

import com.example.newsapp.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun fetchEverything(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse
}