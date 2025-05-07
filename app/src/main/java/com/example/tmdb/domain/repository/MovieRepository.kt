package com.example.tmdb.domain.repository

import com.example.tmdb.core.base.Either
import com.example.tmdb.core.base.Failure
import com.example.tmdb.domain.entity.Movie
import com.example.tmdb.domain.entity.MovieDetail

interface MovieRepository {
    suspend fun getMovies(type: String, page: Int): Either<Failure, List<Movie>>
    suspend fun getMovie(id: Int): Either<Failure, MovieDetail>
    suspend fun getAllFavoriteMovie(): Either<Failure, List<MovieDetail>>
    suspend fun isFavoriteMovie(movieId: Int): Either<Failure, Boolean>
    suspend fun insertFavoriteMovie(movie: MovieDetail): Either<Failure, Unit>
    suspend fun deleteFavoriteMovie(movie: MovieDetail): Either<Failure, Unit>
}