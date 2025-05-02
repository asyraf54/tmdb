package com.example.tmdb.presentation.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmdb.core.constant.MovieType
import com.example.tmdb.core.constant.RequestState
import com.example.tmdb.core.navigation.Screen
import com.example.tmdb.core.widgets.MovieCard
import com.example.tmdb.presentation.list.viewmodel.MovieListViewModel
import com.example.tmdb.presentation.list.viewmodel.NowPlayingViewModel
import com.example.tmdb.presentation.list.viewmodel.PopularMovieViewModel
import com.example.tmdb.presentation.list.viewmodel.TopRatedMovieViewModel
import com.example.tmdb.presentation.list.viewmodel.UpcomingViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val popularViewModel: PopularMovieViewModel = hiltViewModel()
    val nowPlayingViewModel: NowPlayingViewModel = hiltViewModel()
    val upcomingViewModel: UpcomingViewModel = hiltViewModel()
    val topRatedViewModel: TopRatedMovieViewModel = hiltViewModel()
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                MovieSection(movieType = MovieType.popular, navController = navController, viewModel = popularViewModel)
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                MovieSection(movieType = MovieType.topRated, navController = navController, viewModel = topRatedViewModel)
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                MovieSection(movieType = MovieType.nowPlaying, navController = navController, viewModel = nowPlayingViewModel)
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                MovieSection(movieType = MovieType.upcoming, navController = navController, viewModel = upcomingViewModel)
            }
        }

    }
}

@Composable
fun MovieSection(movieType: MovieType, viewModel: MovieListViewModel, navController: NavController){
    val state by viewModel.state.collectAsState()
    LaunchedEffect(movieType) {
        viewModel.getMovies(movieType)
    }

    Column {
        Text("Movie ${movieType.displayName()}")
        Spacer(Modifier.height(12.dp))
        when(state.moviesState.requestState){
            RequestState.loading -> {
                Box(Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            RequestState.success -> {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(state.movies) {
                            movie ->
                        MovieCard(movie)
                    }
                    item {
                        Box(
                            modifier = Modifier
                                .width(128.dp)
                                .height(188.dp)
                                .clickable { navController.navigate(Screen.MovieList.createRoute(
                                    movieType.paramKey())) }
                        ) {
                            Text(
                                text = "Selengkapnya",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                    }
                }
            }
            else -> Unit
        }

    }


}




