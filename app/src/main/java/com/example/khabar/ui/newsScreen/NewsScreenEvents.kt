package com.example.khabar.ui.newsScreen

import com.example.newsapp.domain.model.Article

sealed class NewsScreenEvents {
    data class OnNewsCardClicked(var article: Article) : NewsScreenEvents()
    data class OnCategoryChanged(var category: String) : NewsScreenEvents()
    data class OnSearchQueryChanged(var searchQuery: String) : NewsScreenEvents()
    data object OnSearchIconClicked: NewsScreenEvents()
    data object OnCloseIconClicked: NewsScreenEvents()
}