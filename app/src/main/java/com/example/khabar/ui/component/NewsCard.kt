package com.example.khabar.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.khabar.util.dateFormatter
import com.example.newsapp.domain.model.Article

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: (Article) -> Unit
){
    val date = dateFormatter(article.publishedAt)
    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick(article) }
    ){
        Column (
            modifier = Modifier
                .padding(8.dp)
        ){
            ImageHolder(
                imageUrl = article.urlToImage
            )
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = article.source.name,
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}