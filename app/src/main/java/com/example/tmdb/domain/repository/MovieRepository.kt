package com.example.tmdb.domain.repository

import com.example.tmdb.core.base.Either
import com.example.tmdb.core.base.Failure
import com.example.tmdb.domain.entity.Movie

interface MovieRepository {
    suspend fun getMovies(type: String, page: Int): Either<Failure, List<Movie>>
    suspend fun getMovie(id: Int): Either<Failure, Movie>
}