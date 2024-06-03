package com.example.khabar.data.remote

import com.example.khabar.domain.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "113d334ee2724e98a66bebcc3baeb572"
    }

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("category") category: String
    ): NewsResponse

    @GET("everything")
    suspend fun getSearchedNews(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("q") query: String
    ): NewsResponse

}