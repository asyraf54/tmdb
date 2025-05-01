package com.example.tmdb.domain.usecase

import com.example.tmdb.core.base.Either
import com.example.tmdb.core.base.Failure
import com.example.tmdb.domain.entity.Movie
import com.example.tmdb.domain.repository.MovieRepository
import javax.inject.Inject

class MovieUsecase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend fun getMovies(type: String, page: Int): Either<Failure, List<Movie>> = repository.getMovies(type, page)
    suspend fun getMovieDetail(id: Int): Either<Failure, Movie> = repository.getMovie(id)
}