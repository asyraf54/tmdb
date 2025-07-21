package com.example.tmdb.core.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Dashboard : Screen("dashboard")
    object Home : Screen("home")
    object MovieList : Screen("list/{movieType}") {
        fun createRoute(movieType: String) = "list/$movieType"
    }
    object Detail : Screen("detail/{movieId}") {
        fun createRoute(movieId: Int) = "detail/$movieId"
    }
    object Profile : Screen("profile")
}