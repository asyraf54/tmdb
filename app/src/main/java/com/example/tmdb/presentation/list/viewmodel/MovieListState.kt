package com.example.tmdb.presentation.list.viewmodel

import com.example.tmdb.core.base.BaseState
import com.example.tmdb.domain.entity.Movie

data class MovieListState(
    val moviePage: Int = 1,
    val movies: List<Movie> = listOf(),
    val moviesState: BaseState<List<Movie>> = BaseState(data =  listOf()),
    val moviesNextState: BaseState<List<Movie>> = BaseState(data =  listOf())
)