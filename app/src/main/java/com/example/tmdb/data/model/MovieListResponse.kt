package com.example.tmdb.data.model

data class MovieListResponse(
    val page: Int = 0,
    val results: List<MovieItemResponse> = listOf(),
    val totalPages: Int = 0,
    val totalResults: Int = 0
)