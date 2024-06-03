package com.example.khabar.ui.newsScreen

import com.example.newsapp.domain.model.Article

data class NewsScreenStates(
    val isLoading: Boolean = false,
    var articles: List<Article> = emptyList(),
    val error: String? = null,
    val isSearchBarVisible: Boolean= false,
    val selectedArticle: Article? = null,
    val category: String = "General",
    val searchQuery: String = ""
)