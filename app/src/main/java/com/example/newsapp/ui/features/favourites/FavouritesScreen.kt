package com.example.newsapp.ui.features.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun FavouritesScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Favourites")
    }
}