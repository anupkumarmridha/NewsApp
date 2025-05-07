package com.example.newsapp.domain.model

import java.time.Instant

data class Article(
    val sourceName: String,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val imageUrl: String?,
    val publishedAt: Instant,
    val content: String?
)