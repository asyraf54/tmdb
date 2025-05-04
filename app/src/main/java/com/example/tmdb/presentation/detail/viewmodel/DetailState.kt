package com.example.tmdb.presentation.detail.viewmodel

import com.example.tmdb.core.base.BaseState
import com.example.tmdb.domain.entity.MovieDetail

data class DetailState(
    val detailState: BaseState<MovieDetail?> = BaseState(data =  null),
)