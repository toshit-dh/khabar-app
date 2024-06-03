package com.example.khabar.domain.repository

import com.example.khabar.util.Resource
import com.example.newsapp.domain.model.Article
import retrofit2.http.Query

interface NewsRepository {

    suspend fun getTopHeadline(
        category: String
    ): Resource<List<Article>>

    suspend fun getSearchedNews(
        query: String
    ): Resource<List<Article>>

}