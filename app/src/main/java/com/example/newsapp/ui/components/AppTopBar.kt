package com.example.newsapp.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.newsapp.R


@Composable
fun AppTopBar(
    @DrawableRes logoRes: Int,
    onLogoClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onSearch: (String) -> Unit = {}
) {
    // State to track if search is active
    var isSearchActive by remember { mutableStateOf(false) }
    // State to store search query
    var searchQuery by remember { mutableStateOf("") }
    // State to store recent searches (max 5 items)
    var recentSearches by remember { mutableStateOf(listOf<String>()) }

    Column {
        // Top bar with fixed height
        Surface(
            color = Color.White,
            tonalElevation = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            if (isSearchActive) {
                // Search bar UI
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearch = {
                        // Add to recent searches if not empty
                        if (searchQuery.isNotEmpty()) {
                            recentSearches = (listOf(searchQuery) + recentSearches)
                                .distinct()
                                .take(5) // Keep only 5 recent searches
                        }
                        onSearch(searchQuery)
                    },
                    onClose = {
                        isSearchActive = false
                        searchQuery = ""
                    }
                )
            } else {
                // Normal top bar UI
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Left: Logo
                    Icon(
                        painter = painterResource(id = logoRes),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(36.dp)
                            .clickable(onClick = onLogoClick),
                        tint = Color.Unspecified
                    )

                    // Right: Search + Notifications
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        IconButtonCircle(
                            iconRes = R.drawable.ic_search,
                            contentDescription = "Search",
                            onClick = {
                                isSearchActive = true
                                onSearchClick()
                            }
                        )
                        IconButtonCircle(
                            iconRes = R.drawable.ic_notifications,
                            contentDescription = "Notifications",
                            onClick = onNotificationClick
                        )
                    }
                }
            }
        }

        // Recent searches shown below the top bar when search is active
        if (isSearchActive) {
            Surface(
                color = Color.White,
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                RecentSearches(
                    recentSearches = recentSearches,
                    onSearchItemClick = { query ->
                        searchQuery = query
                        onSearch(query)
                        isSearchActive = false
                    },
                    onClearAll = { recentSearches = emptyList() }
                )
            }
        }
    }
}

@Composable
private fun IconButtonCircle(
    @DrawableRes iconRes: Int,
    contentDescription: String,
    onClick: () -> Unit
) {
    Box(
        Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp),
            tint = Color.Black
        )
    }
}

