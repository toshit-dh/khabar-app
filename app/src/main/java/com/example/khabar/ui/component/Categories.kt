package com.example.khabar.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Categories(
    pagerState: PagerState,
    categories: List<String>,
    onCategorySelected: (Int) -> Unit
){
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        edgePadding = 0.dp,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        categories.forEachIndexed { index, s ->
            Tab(
                content = {
                       Text(
                           modifier = Modifier
                               .padding(vertical = 8.dp, horizontal = 2.dp),
                           text = s
                       )
                },
                selected = pagerState.currentPage == index,
                onClick = { onCategorySelected(index) }
            )
        }
    }
}