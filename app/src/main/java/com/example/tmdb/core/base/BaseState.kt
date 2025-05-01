package com.example.tmdb.core.base

import com.example.tmdb.core.constant.RequestState

data class BaseState<T>(
    val requestState: RequestState = RequestState.initial,
    val data: T,
    val message: String = ""
)