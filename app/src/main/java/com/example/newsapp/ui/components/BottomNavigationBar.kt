package com.example.newsapp.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.newsapp.R
import com.example.newsapp.ui.navigation.AppScreens

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