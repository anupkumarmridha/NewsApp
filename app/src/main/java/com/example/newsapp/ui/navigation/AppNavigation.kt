package com.example.newsapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.newsapp.ui.features.home.ArticleDetailScreen
import com.example.newsapp.ui.features.home.HomeScreen
import com.example.newsapp.ui.features.profile.ProfileScreen
import kotlin.text.get


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    innerPadding: PaddingValues
) {
    Navigation(navController,innerPadding)
}

@RequiresApi(Build.VERSION_CODES.O)
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
        composable(AppScreens.Home) {
            HomeScreen(navController=navController)
        }

        composable(AppScreens.Detail) { backStackEntry ->
            val article = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<com.example.newsapp.domain.model.Article>("article")
            article?.let {
                ArticleDetailScreen(article = it)
            }
        }
        composable(AppScreens.Favourites) {
            FavouritesScreen()
        }
        composable(AppScreens.Discover) {
            DiscoverScreen()
        }
        composable(AppScreens.Profile) {
            ProfileScreen()
        }
    }
}