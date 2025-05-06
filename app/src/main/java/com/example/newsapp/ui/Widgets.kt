package com.example.newsapp.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.example.newsapp.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.newsapp.ui.navigation.AppScreens

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.ImeAction

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val tabs = listOf(
        TabItem(AppScreens.Home, R.drawable.ic_home_outline, R.drawable.ic_home_filled, "Home"),
        TabItem(AppScreens.Favourites, R.drawable.ic_favourites_outline, R.drawable.ic_favourites_filled, "Favourites"),
        TabItem(AppScreens.Discover, R.drawable.ic_discover_outline, R.drawable.ic_discover_filled, "Discover"),
        TabItem(AppScreens.Profile, R.drawable.ic_profile_outline, R.drawable.ic_profile_filled, "Profile"),
    )

    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route

    NavigationBar(
        modifier       = Modifier.fillMaxWidth(),
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        tabs.forEach { tab ->
            val selected = currentRoute == tab.route
            val iconToDisplay = if (selected) tab.selectedIconRes else tab.iconRes // Choose icon based on selection

            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = iconToDisplay), // Use the determined icon
                        contentDescription = tab.label,
                        modifier         = Modifier.size(28.dp)
                    )
                },
                label = {
                    Text(text = tab.label, fontSize = 12.sp)
                },
                alwaysShowLabel = true,
                selected        = selected,
                onClick         = {
                    if (!selected) {
                        navController.navigate(tab.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState    = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = Color(0xFF2962FF),
                    unselectedIconColor = Color.Black,
                    selectedTextColor   = Color(0xFF2962FF),
                    unselectedTextColor = Color.Black,
                    indicatorColor      = Color.Transparent
                )
            )
        }
    }
}


private data class TabItem(
    val route: String,
    @DrawableRes val iconRes: Int,
    @DrawableRes val selectedIconRes: Int,
    val label: String
)

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

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClose: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back button
        IconButton(onClick = onClose) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back), // Use appropriate back icon from resources
                contentDescription = "Close Search"
            )
        }

        // Search input field
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch() }
            ),
            decorationBox = { innerTextField ->
                Box {
                    if (query.isEmpty()) {
                        Text(
                            text = "Search news...",
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            }
        )

        // Clear button (only when there's text)
        if (query.isNotEmpty()) {
            IconButton(onClick = { onQueryChange("") }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clear),
                    contentDescription = "Clear Search"
                )
            }
        }

        // Search button
        IconButton(onClick = onSearch) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Execute Search"
            )
        }
    }
}

@Composable
fun RecentSearches(
    recentSearches: List<String>,
    onSearchItemClick: (String) -> Unit,
    onClearAll: () -> Unit
) {
    if (recentSearches.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Searches",
                style = MaterialTheme.typography.titleSmall
            )

            TextButton(onClick = onClearAll) {
                Text("Clear All")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.heightIn(max = 200.dp)
        ) {
            items(recentSearches) { searchQuery ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSearchItemClick(searchQuery) }
                        .padding(vertical = 12.dp, horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_history),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = searchQuery)
                }

                if (recentSearches.indexOf(searchQuery) < recentSearches.size - 1) {
                    HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                }
            }
        }
    }
}





