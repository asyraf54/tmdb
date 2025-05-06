package com.example.tmdb.core.utils.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdb.data.model.MovieDetailResponse
import com.example.tmdb.data.source.MovieLocalSource

@Database(entities = [MovieDetailResponse::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieLocalSource(): MovieLocalSource
}
