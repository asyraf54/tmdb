package com.example.tmdb.presentation.list.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmdb.core.constant.MovieType
import com.example.tmdb.core.constant.RequestState
import com.example.tmdb.core.widgets.MovieCard
import com.example.tmdb.presentation.list.viewmodel.MovieListViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    type: String,
    navController: NavController,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val movieType : MovieType = MovieType.getFromType(type)

    LaunchedEffect(type) {
        viewModel.getMovies(movieType)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    // Tampilkan ikon back jika memungkinkan
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                title = {
                    Text("Movie ${movieType.displayName()}")
                }
            )
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)){
            when (state.moviesState.requestState) {
                RequestState.loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                RequestState.success -> {
                    val movies = state.movies
                    val gridState = rememberLazyGridState()


                    LaunchedEffect(gridState) {
                        snapshotFlow { gridState.layoutInfo }
                            .map { layoutInfo ->
                                val visibleItems = layoutInfo.visibleItemsInfo
                                val totalItems = layoutInfo.totalItemsCount
                                val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: 0
                                lastVisibleItemIndex to totalItems
                            }
                            .distinctUntilChanged()
                            .collect { (lastVisible, total) ->
                                if (lastVisible >= total - 3 && state.moviesNextState.requestState != RequestState.loading) {
                                    viewModel.getNextMovies(movieType)
                                }
                            }
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        state = gridState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(movies) { movie ->
                            MovieCard(movie)
                        }
                    }
                }

                RequestState.error -> {
                    val message = state.moviesState.message
                    Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        Text("Error: $message")
                    }
                }

                else -> Unit
            }
        }
    }



}