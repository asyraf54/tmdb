package com.example.tmdb.presentation.detail.viewmodel

import com.example.tmdb.core.base.BaseState
import com.example.tmdb.domain.entity.Movie

data class DetailState(
    val detailState: BaseState<Movie?> = BaseState(data =  null),
)