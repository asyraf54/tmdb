package com.example.tmdb.domain.usecase

import com.example.tmdb.core.base.Either
import com.example.tmdb.core.base.Failure
import com.example.tmdb.domain.entity.Movie
import com.example.tmdb.domain.entity.MovieDetail
import com.example.tmdb.domain.repository.MovieRepository
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend fun getMovies(type: String, page: Int): Either<Failure, List<Movie>> =
        repository.getMovies(type, page)

    suspend fun getMovieDetail(id: Int): Either<Failure, MovieDetail> = repository.getMovie(id)

    suspend fun getAllFavoriteMovie(): Either<Failure, List<MovieDetail>> =
        repository.getAllFavoriteMovie()

    suspend fun isFavoriteMovie(movieId: Int): Either<Failure, Boolean> =
        repository.isFavoriteMovie(movieId = movieId)

    suspend fun insertFavoriteMovie(movie: MovieDetail): Either<Failure, Unit> =
        repository.insertFavoriteMovie(movie)

    suspend fun deleteFavoriteMovie(movie: MovieDetail): Either<Failure, Unit> =
        repository.deleteFavoriteMovie(movie)
}