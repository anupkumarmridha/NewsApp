package com.example.newsapp.redux

import com.example.newsapp.domain.model.Article

data class AppState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)