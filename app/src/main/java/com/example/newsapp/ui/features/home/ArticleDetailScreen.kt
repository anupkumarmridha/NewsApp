package com.example.newsapp.ui.features.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.newsapp.domain.model.Article
import com.example.newsapp.ui.formatDate
import androidx.compose.ui.platform.LocalUriHandler

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticleDetailScreen(article: Article) {
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        AsyncImage(
            model = article.imageUrl,
            contentDescription = article.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(Modifier.height(12.dp))
        Text(text = article.title, style = MaterialTheme.typography.headlineSmall)
        Text(
            text = formatDate(article.publishedAt.toEpochMilli()),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
        Spacer(Modifier.height(8.dp))
        article.author?.let {
            Text(text = "By $it", fontWeight = FontWeight.Light)
        }
        Spacer(Modifier.height(8.dp))
        Text(text = article.description.orEmpty(), style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(8.dp))
        Text(text = article.content.orEmpty(), style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Source: ${article.sourceName}",
            style = MaterialTheme.typography.labelSmall
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "Read more",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                uriHandler.openUri(article.url)
            }
        )
    }
}