package com.example.tmdb.presentation.home.viewmodel

import com.example.tmdb.core.base.BaseState
import com.example.tmdb.domain.entity.Movie

data class HomeState(
    val moviesPopularPage: Int = 1,
    val moviesPopular: List<Movie> = listOf(),
    val moviePopularState: BaseState<List<Movie>> = BaseState(data =  listOf()),
    val movieNextPopularState: BaseState<List<Movie>> = BaseState(data =  listOf())
)