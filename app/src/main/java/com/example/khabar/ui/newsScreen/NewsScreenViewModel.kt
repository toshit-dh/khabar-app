package com.example.khabar.ui.newsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khabar.domain.repository.NewsRepository
import com.example.khabar.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsScreenViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    var state by mutableStateOf(NewsScreenStates())

    private var searchJob: Job? = null

    init {
        getTopHeadlines("general")
    }

    private fun getTopHeadlines(category: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            state = when (val result = newsRepository.getTopHeadline(category)) {
                is Resource.Error -> {
                    state.copy(
                        error = result.message ?: "An unknown error occurred",
                        isLoading = false,
                    )
                }

                is Resource.Success -> {
                    state.copy(
                        articles = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }

    private fun getSearchedNews(query: String) {
        state.articles = emptyList()
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            state = when (val result = newsRepository.getSearchedNews(query)) {
                is Resource.Error -> {
                    state.copy(
                        error = result.message ?: "An unknown error occurred",
                        isLoading = false,
                    )
                }

                is Resource.Success -> {
                    state.copy(
                        articles = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }

    fun onEvent(event: NewsScreenEvents) {
        when (event) {
            is NewsScreenEvents.OnCategoryChanged -> {
                state = state.copy(
                    category = event.category
                )
                getTopHeadlines(event.category)
            }

            NewsScreenEvents.OnCloseIconClicked -> {
                state = state.copy(
                    isSearchBarVisible = false
                )
                getTopHeadlines(state.category)
            }

            is NewsScreenEvents.OnNewsCardClicked -> {
                state = state.copy(
                    selectedArticle = event.article
                )
            }

            NewsScreenEvents.OnSearchIconClicked -> {
                state = state.copy(
                    isSearchBarVisible = true
                )
                state.articles = emptyList()
            }

            is NewsScreenEvents.OnSearchQueryChanged -> {
                state = state.copy(
                    searchQuery = event.searchQuery
                )
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(1000)
                    getSearchedNews(event.searchQuery)
                }
            }
        }
    }
}
