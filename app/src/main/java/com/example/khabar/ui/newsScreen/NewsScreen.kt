package com.example.khabar.ui.newsScreen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.khabar.R
import com.example.khabar.ui.component.BottomSheet
import com.example.khabar.ui.component.Categories
import com.example.khabar.ui.component.NewsCard
import com.example.khabar.ui.component.RetryContent
import com.example.khabar.ui.component.SearchBar
import com.example.khabar.util.Constants.categories
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NewsScreen(
    states: NewsScreenStates,
    onEvent: (NewsScreenEvents) -> Unit,
    onReadFullArticle: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = {
            categories.size
        }
    )
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    if (showBottomSheet)
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            states.selectedArticle?.let {
                BottomSheet(
                    article = it,
                    readFullArticle = {
                        onReadFullArticle(it.url)
                        coroutineScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) showBottomSheet = false
                        }
                    }
                )
            }
        }

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            onEvent(NewsScreenEvents.OnCategoryChanged(categories[it]))
        }
    }
    LaunchedEffect(key1 = Unit) {
        if (states.searchQuery.isNotEmpty())
            onEvent(NewsScreenEvents.OnSearchQueryChanged(states.searchQuery))
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Crossfade(
            targetState = states.isSearchBarVisible,
            label = stringResource(R.string.crossfade_switch)
        ) { ts ->
            if (ts) {
                Scaffold {
                    Column(
                        modifier = Modifier
                            .padding(it)
                    ) {
                        SearchBar(
                            modifier = Modifier
                                .focusRequester(focusRequester),
                            value = states.searchQuery,
                            onValueChange = {
                                onEvent(NewsScreenEvents.OnSearchQueryChanged(it))
                            },
                            onSearch = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            },
                            onClose = {
                                onEvent(NewsScreenEvents.OnCloseIconClicked)
                            }
                        )
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            items(states.articles) { a ->
                                NewsCard(
                                    article = a,
                                    onClick = {
                                        showBottomSheet = true
                                        onEvent(NewsScreenEvents.OnNewsCardClicked(it))
                                    }
                                )
                            }
                        }
                    }
                }
            } else {
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        NewsScreenTopBar(
                            scrollBehavior = scrollBehavior,
                            onSearch = {
                                coroutineScope.launch {
                                    delay(500)
                                    focusRequester.requestFocus()
                                }
                                onEvent(NewsScreenEvents.OnSearchIconClicked)
                            }
                        )
                    }
                ) { pv ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(pv)
                    ) {
                        Categories(
                            pagerState = pagerState,
                            categories = categories,
                            onCategorySelected = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(it)
                                }
                            }
                        )
                        HorizontalPager(
                            state = pagerState,
                        ) {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                items(states.articles) { a ->
                                    NewsCard(
                                        article = a,
                                        onClick = {
                                            showBottomSheet = true
                                            onEvent(NewsScreenEvents.OnNewsCardClicked(it))
                                        }
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                if (states.error != null)
                                    RetryContent(
                                        error = states.error,
                                        onRetry = {
                                            onEvent(NewsScreenEvents.OnCategoryChanged(states.category))
                                        }
                                    )
                            }
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsScreenTopBar(
    onSearch: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.khabar),
                fontWeight = FontWeight.Bold,
            )
        },
        actions = {
            IconButton(
                onClick = onSearch
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    )
}

