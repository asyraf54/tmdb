package com.example.tmdb.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdb.data.model.MovieDetailModel

@Dao
interface MovieLocalSource {
    @Query("SELECT * FROM movies")
    suspend fun getAllFavoriteMovie(): List<MovieDetailModel>

    @Query("SELECT EXISTS(SELECT 1 FROM movies WHERE id = :movieId)")
    suspend fun isFavoriteMovie(movieId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: MovieDetailModel)

    @Delete
    suspend fun deleteFavoriteMovie(movie: MovieDetailModel)
}