package com.example.newsapp.data.repository
import android.util.Log
import com.example.newsapp.BuildConfig
import com.example.newsapp.data.api.NewsApi
import com.example.newsapp.data.mapper.toDomain
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImp @Inject constructor(
    private val newsApi: NewsApi
): NewsRepository {
    override suspend fun getNews(query: String): List<Article> {
        Log.d("NewsRepositoryImp", "Attempting to use API Key: '${API_KEY}'")
        val resp = newsApi.fetchEverything(query, API_KEY)
        if (resp.status != "ok") {
            Log.e("NewsRepositoryImp", "NewsAPI request failed with status: ${resp.status}")
            throw RuntimeException("NewsAPI error: ${resp.status}")
        }
        Log.d("NewsRepository", "Fetched ${resp.articles.size} articles")
        return resp.articles.map { it.toDomain() }
    }

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }
}