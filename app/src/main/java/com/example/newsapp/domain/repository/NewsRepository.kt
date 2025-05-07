package com.example.newsapp.domain.repository

import com.example.newsapp.domain.model.Article

interface NewsRepository {
    suspend fun getNews(query: String): List<Article>
}