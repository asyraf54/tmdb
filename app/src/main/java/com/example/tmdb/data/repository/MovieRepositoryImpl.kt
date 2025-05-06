package com.example.tmdb.data.repository

import com.example.tmdb.core.base.Either
import com.example.tmdb.core.base.Failure
import com.example.tmdb.core.utils.mapper.mapExceptionToFailure
import com.example.tmdb.data.model.MovieDetailResponse
import com.example.tmdb.data.source.MovieLocalSource
import com.example.tmdb.data.source.MovieRemoteSource
import com.example.tmdb.domain.entity.Movie
import com.example.tmdb.domain.entity.MovieDetail
import com.example.tmdb.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteSource: MovieRemoteSource,
    private val localSource: MovieLocalSource
) : MovieRepository {
    override suspend fun getMovies(type: String, page: Int): Either<Failure, List<Movie>> = try {
        Either.Right(remoteSource.getMovies(type, page).results.map { it.toEntity() })
    } catch (e: Exception) {
        Either.Left(mapExceptionToFailure(e))
    }

    override suspend fun getMovie(id: Int): Either<Failure, MovieDetail> = try {
        Either.Right(remoteSource.getMovie(id).toEntity())
    } catch (e: Exception) {
        Either.Left(mapExceptionToFailure(e))
    }

    override suspend fun getAllFavoriteMovie(): Either<Failure, List<MovieDetail>> = try {
        val movies = localSource.getAllFavoriteMovie().map { it.toEntity() }
        Either.Right(movies)
    } catch (e: Exception) {
        Either.Left(mapExceptionToFailure(e))
    }

    override suspend fun isFavoriteMovie(movieId: Int): Either<Failure, Boolean> = try {
        val result = localSource.isFavoriteMovie(movieId)
        Either.Right(result)
    } catch (e: Exception) {
        Either.Left(mapExceptionToFailure(e))
    }

}
