package com.example.newsapp.redux

import com.example.newsapp.domain.model.Article

sealed class NewsAction {
    data class Loading(val query: String) : NewsAction()
    data class NewsLoadedSuccess(val articles: List<Article>) : NewsAction()
    data class Error(val message: String) : NewsAction()
}