package com.example.tmdb.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.tmdb.presentation.dashboard.DashboardScreen
import com.example.tmdb.presentation.detail.ui.DetailScreen
import com.example.tmdb.presentation.list.ui.MovieListScreen
import com.example.tmdb.presentation.splash.ui.SplashScreen
import kotlinx.coroutines.delay

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen()

            // Auto navigate to Home after delay
            LaunchedEffect(true) {
                delay(3000) // 2 seconds splash delay
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(navController= navController)
        }

        composable(
            route = Screen.MovieList.route,
            arguments = listOf(navArgument("movieType") { type = NavType.StringType }),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "myapp://list/{movieType}"
                }
            )
        ) { backStackEntry ->
            val movieType = backStackEntry.arguments?.getString("movieType") ?: ""
            MovieListScreen(type = movieType, navController = navController)
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.StringType }),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "myapp://detail/{movieId}"
                }
            )
        ) { backStackEntry ->
            val movieId =
                backStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: 0
            DetailScreen(movieId = movieId, navController = navController)
        }
    }
}
