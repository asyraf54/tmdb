package com.example.tmdb.presentation.home.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.tmdb.core.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                title = {
                    Text("Movie App")
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                navController.navigate(Screen.MovieList.createRoute("popular"))
            }) {
                Text("Popular")
            }

            Button(onClick = {
                navController.navigate(Screen.MovieList.createRoute("now_playing"))
            }) {
                Text("Now Playing")
            }

            Button(onClick = {
                navController.navigate(Screen.MovieList.createRoute("upcoming"))
            }) {
                Text("Upcoming")
            }
        }
    }

}




