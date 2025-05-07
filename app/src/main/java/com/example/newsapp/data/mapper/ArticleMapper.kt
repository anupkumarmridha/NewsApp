package com.example.newsapp.data.mapper

import com.example.newsapp.data.model.ArticleDto
import com.example.newsapp.domain.model.Article
import java.time.Instant


fun ArticleDto.toDomain(): Article = Article(
    sourceName  = source.name,
    author      = author,
    title       = title,
    description = description,
    url         = url,
    imageUrl    = urlToImage,
    publishedAt = Instant.parse(publishedAt),
    content     = content
)