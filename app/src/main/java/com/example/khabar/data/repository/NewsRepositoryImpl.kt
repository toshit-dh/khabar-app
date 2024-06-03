package com.example.khabar.data.repository

import com.example.khabar.data.remote.NewsApi
import com.example.khabar.domain.repository.NewsRepository
import com.example.khabar.util.Resource
import com.example.newsapp.domain.model.Article

class NewsRepositoryImpl(
    private val newsApi: NewsApi
) : NewsRepository {
    override suspend fun getTopHeadline(
        category: String
    ): Resource<List<Article>> {
        return try {
            val res = newsApi.getTopHeadlines(category = category)
            Resource.Success(res.articles)
        } catch (e: Exception) {
            Resource.Error(message = "Failed to fetch news. ${e.message}")
        }
    }

    override suspend fun getSearchedNews(
        query: String
    ): Resource<List<Article>> {
        return try {
            val res = newsApi.getSearchedNews(query = query)
            Resource.Success(res.articles)
        } catch (e: Exception) {
            Resource.Error(message = "Failed to fetch news. ${e.message}")
        }
    }
}
