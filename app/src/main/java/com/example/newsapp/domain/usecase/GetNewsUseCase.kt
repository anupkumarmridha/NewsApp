package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import javax.inject.Inject


class GetNewsUseCase @Inject constructor(
    private val repo: NewsRepository
) {
    suspend operator fun invoke(query: String): List<Article> =
        repo.getNews(query)
}