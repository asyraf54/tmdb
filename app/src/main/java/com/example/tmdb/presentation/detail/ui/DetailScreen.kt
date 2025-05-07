package com.example.tmdb.presentation.detail.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmdb.core.constant.RequestState
import com.example.tmdb.core.widgets.AsyncImageBase
import com.example.tmdb.domain.entity.MovieDetail
import com.example.tmdb.presentation.detail.viewmodel.DetailViewModel
import com.example.tmdb.presentation.favorite.viewmodel.FavoriteViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(movieId: Int, navController: NavController) {
    val viewModel: DetailViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    var debounceJob: Job? by remember { mutableStateOf(null) }
    val coroutineScope = rememberCoroutineScope()
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val favoriteState by favoriteViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initialize(movieId)
        favoriteViewModel.isFavoriteMovie(movieId)
    }

    Scaffold(

    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (state.detailState.requestState) {
                RequestState.loading -> {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                RequestState.success -> {
                    val data = state.detailState.data
                    if (data != null) {
                        val movie: MovieDetail = data
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState)
                        ) {
                            // Poster and Back Button
                            Box {
                                AsyncImageBase(
                                    imageUrl = "https://image.tmdb.org/t/p/original${movie.posterPath}",
                                    contentDescription = "Movie Poster",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(360.dp),
                                    contentScale = ContentScale.Crop
                                )

                                // Back button - top left
                                IconButton(
                                    onClick = {
                                        if (navController.previousBackStackEntry != null) {
                                            navController.popBackStack()
                                        }
                                    },
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(16.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back",
                                        tint = Color.White
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        favoriteViewModel.setIsFavorite(!favoriteState.isFavorite)
                                        debounceJob?.cancel()
                                        debounceJob = coroutineScope.launch {
                                            delay(500L)
                                            if(favoriteState.isFavorite){
                                                favoriteViewModel.insertFavoriteMovie(movie)
                                            } else {
                                                favoriteViewModel.deleteFavoriteMovie(movie)
                                            }
                                        } },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(16.dp)
                                ) {
                                    Icon(
                                        imageVector = if (favoriteState.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Favorite",
                                        tint = if (favoriteState.isFavorite) Color.Red else Color.White
                                    )
                                }
                            }


                            // Title and Progress
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    CircularProgressIndicator(
                                        progress = { 0.45f },
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(end = 8.dp),
                                        strokeWidth = 4.dp,
                                    )
                                    Column {
                                        Text(
                                            movie.title,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            movie.releaseDate,
                                            fontSize = 14.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // Description
                                Text(
                                    text = movie.overview,
                                    fontSize = 14.sp
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // Trailer Button
                                Button(
                                    onClick = { /* watch trailer */ },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Watch Trailer")
                                }

                                Spacer(modifier = Modifier.height(24.dp))

//                                // Cast
//                                Text("Elenco principal", color = Color.White, fontWeight = FontWeight.Bold)
//                                LazyRow {
//                                    items(5) {
//                                        Column(
//                                            modifier = Modifier.padding(8.dp),
//                                            horizontalAlignment = Alignment.CenterHorizontally
//                                        ) {
//                                            AsyncImageBase(
//                                                imageUrl = "",
//                                                contentDescription = null,
//                                                modifier = Modifier
//                                                    .size(60.dp)
//                                                    .clip(CircleShape)
//                                                    .border(2.dp, Color.Gray, CircleShape)
//                                            )
//                                            Text("Sandra Bullock", color = Color.White, fontSize = 12.sp)
//                                        }
//                                    }
//                                }
//
//                                Spacer(modifier = Modifier.height(16.dp))
//
//
//                                // Recommendations
//                                Text("Recommendations", color = Color.White, fontWeight = FontWeight.Bold)
//                                LazyRow {
//                                    items(3) {
//                                        AsyncImageBase(
//                                            imageUrl = "",
//                                            contentDescription = null,
//                                            modifier = Modifier
//                                                .size(width = 100.dp, height = 150.dp)
//                                                .padding(end = 8.dp)
//                                        )
//                                    }
//                                }
                            }
                        }
                    } else {
                        Unit
                    }
                }

                else -> Unit
            }

        }
    }
}
