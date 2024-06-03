package com.example.newsapp.domain.model

import com.example.khabar.domain.model.Source

data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String?
)