package com.example.tmdb.presentation.favorite.viewmodel

import com.example.tmdb.core.base.BaseState
import com.example.tmdb.domain.entity.MovieDetail

data class FavoriteState(
    val isFavorite: Boolean = false,
    val getAllFavoriteMovieState: BaseState<List<MovieDetail>> = BaseState(data = listOf()),
    val insertFavoriteMovieState: BaseState<Unit?> = BaseState(data = null),
    val deleteFavoriteMovieState: BaseState<Unit?> = BaseState(data = null),
)