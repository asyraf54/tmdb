package com.example.tmdb.presentation.home.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tmdb.core.constant.RequestState
import com.example.tmdb.core.theme.TMDBTheme
import com.example.tmdb.domain.entity.Movie
import com.example.tmdb.presentation.home.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map


@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getMovies("popular")

        setContent {
            TMDBTheme(darkTheme = true) {
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
                    HomeScreen(
                        modifier = Modifier.padding(padding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    val state by viewModel.state.collectAsState()


    when (state.moviePopularState.requestState) {
        RequestState.loading -> {
            Box(modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        RequestState.success -> {
            val movies = state.moviesPopular
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
                        if (lastVisible >= total - 3 && state.movieNextPopularState.requestState != RequestState.loading) {
                            viewModel.getNextMovies("popular")
                        }
                    }
            }


            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(8.dp),
                state = gridState,
                modifier = modifier.fillMaxSize()
            ) {
                items(movies) { movie ->
                    MovieItem(movie)
                }
            }
        }

        RequestState.error -> {
            val message = state.moviePopularState.message
            Box(modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Error: $message")
            }
        }

        else -> Unit
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original${movie.posterPath}",
            contentDescription = movie.title,
            modifier = Modifier
                .width(128.dp)
                .height(188.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1
        )
    }
}

