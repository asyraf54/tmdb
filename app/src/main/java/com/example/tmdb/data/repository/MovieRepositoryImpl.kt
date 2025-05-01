package com.example.tmdb.data.repository

import com.example.tmdb.core.base.Either
import com.example.tmdb.core.base.Failure
import com.example.tmdb.core.utils.mapper.mapExceptionToFailure
import com.example.tmdb.data.source.MovieSource
import com.example.tmdb.domain.entity.Movie
import com.example.tmdb.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val source: MovieSource) : MovieRepository {
    override suspend fun getMovies(type: String, page: Int): Either<Failure, List<Movie>> = try {
        Either.Right(source.getMovies(type, page).results.map { it.toEntity() })
    } catch (e: Exception) {
        Either.Left(mapExceptionToFailure(e))
    }

    override suspend fun getMovie(id: Int): Either<Failure, Movie> = try {
        Either.Right(source.getMovie(id).toEntity())
    } catch (e: Exception) {
        Either.Left(mapExceptionToFailure(e))
    }
}
