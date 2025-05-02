package com.example.tmdb.presentation.detail.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.tmdb.core.widgets.TopAppBarBase

@Composable
fun DetailScreen(movieId: Int, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBarBase(navController= navController, title = "Movie Detail")
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding))
    }
}