package com.example.newsapp.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.ui.features.discover.DiscoverScreen
import com.example.newsapp.ui.features.favourites.FavouritesScreen
import com.example.newsapp.ui.features.home.HomeScreen
import com.example.newsapp.ui.features.profile.ProfileScreen


@Composable
fun AppNavigation(innerPadding: PaddingValues) {
    val navController = rememberNavController()
    Navigation(navController,innerPadding)
}

@Composable
fun Navigation(navController: NavHostController,innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Home, // Use the string constant
        modifier = Modifier.padding(innerPadding),
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            ) + fadeIn(animationSpec = tween(400))
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            ) + fadeOut(animationSpec = tween(400))
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            ) + fadeIn(animationSpec = tween(400))
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            ) + fadeOut(animationSpec = tween(400))
        },
    ) {
        composable(AppScreens.Home) { // Use the string constant
            HomeScreen(navController)
        }
        composable(AppScreens.Favourites) { // Use the string constant
            FavouritesScreen()
        }
        composable(AppScreens.Discover) { // Use the string constant
            DiscoverScreen()
        }
        composable(AppScreens.Profile) { // Use the string constant
            ProfileScreen()
        }
    }
}