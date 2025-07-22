package com.example.tmdb.presentation.dashboard

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.core.constant.Constants
import com.example.tmdb.core.navigation.Screen
import com.example.tmdb.core.widgets.BottomNavigationBar
import com.example.tmdb.presentation.profile.ui.ProfileScreen
import com.example.tmdb.presentation.home.ui.HomeScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun DashboardScreen(navController: NavHostController) {
    val navC = rememberNavController()
    Scaffold(
        topBar = {
            Spacer(Modifier.height(0.dp))
        },
        // Bottom navigation
        bottomBar = {
            BottomNavigationBar(navController = navC, bottomNavItems = Constants.BottomNavItems)
        }, content = { padding ->
            // Nav host: where screens are placed
            NavHost(
                navController = navC,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(navController)
                }
                composable(Screen.Profile.route) {
                    ProfileScreen()
                }
            }
        }
    )
}
