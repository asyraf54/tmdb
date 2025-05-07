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
import com.example.tmdb.core.widgets.TopAppBarBase
import com.example.tmdb.domain.entity.Movie
import com.example.tmdb.presentation.favorite.viewmodel.FavoriteViewModel
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
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        popularViewModel.initialize(MovieType.popular)
        nowPlayingViewModel.initialize(MovieType.nowPlaying)
        upcomingViewModel.initialize(MovieType.upcoming)
        topRatedViewModel.initialize(MovieType.topRated)
        favoriteViewModel.getAllFavoriteMovie()
    }

    Scaffold(
        topBar = {
            TopAppBarBase(navController= navController, title = "Movie App")
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {

            item {
                FavoriteSection(navController = navController, viewModel = favoriteViewModel)
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
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
fun FavoriteSection( viewModel: FavoriteViewModel, navController: NavController){
    val state by viewModel.state.collectAsState()

    when(state.getAllFavoriteMovieState.requestState){
        RequestState.loading -> {
            Box(Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        RequestState.success -> {
            if(state.getAllFavoriteMovieState.data.isNotEmpty()){
                Column {
                    Text("Movie Favorite")
                    Spacer(Modifier.height(12.dp))


                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(state.getAllFavoriteMovieState.data) {
                                movie ->
                            MovieCard(movie = Movie(
                                id = movie.id,
                                title = movie.title,
                                overview = movie.overview,
                                posterPath = movie.posterPath,
                                backdropPath = movie.backdropPath,
                                genreIds = listOf(),
                                releaseDate = movie.releaseDate,
                                voteAverage = movie.voteAverage,
                                voteCount = movie.voteCount,
                                popularity = movie.popularity
                            ), onClick = {navController.navigate(Screen.Detail.createRoute(
                                movie.id))})
                        }
                    }
                }
            }
            else {
                Unit
            }
        }
        else -> Unit
    }

}

@Composable
fun MovieSection( movieType: MovieType, viewModel: MovieListViewModel, navController: NavController){
    val state by viewModel.state.collectAsState()

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
                        MovieCard(movie, onClick = {navController.navigate(Screen.Detail.createRoute(
                            movie.id))})
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




