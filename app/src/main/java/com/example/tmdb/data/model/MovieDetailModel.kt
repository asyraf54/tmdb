package com.example.tmdb.data.model

import com.example.tmdb.domain.entity.MovieDetail

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDetailModel(
    val backdropPath: String?,
    val budget: Int,
    @PrimaryKey val id: Int,
    val imdbId: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String?,
    val revenue: Int,
    val runtime: Int,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
){
    fun toEntity(): MovieDetail {
        return MovieDetail(
            adult = false,
            backdropPath = backdropPath.orEmpty(),
            belongsToCollection = Any(),
            budget = budget,
            genres = emptyList(),
            homepage = "",
            id = id,
            imdbId = imdbId.orEmpty(),
            originCountry = emptyList(),
            originalLanguage = originalLanguage.orEmpty(),
            originalTitle = originalTitle.orEmpty(),
            overview = overview.orEmpty(),
            popularity = popularity,
            posterPath = posterPath.orEmpty(),
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            releaseDate = releaseDate.orEmpty(),
            revenue = revenue,
            runtime = runtime,
            spokenLanguages = emptyList(),
            status = status.orEmpty(),
            tagline = tagline.orEmpty(),
            title = title.orEmpty(),
            video = video == true,
            voteAverage = voteAverage,
            voteCount = voteCount,
        )
    }
}

fun MovieDetail.toDao(): MovieDetailModel =
    MovieDetailModel(
        backdropPath = backdropPath,
        budget = budget,
        id = id,
        imdbId = imdbId,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        revenue = revenue,
        runtime = runtime,
        status = status,
        tagline = tagline,
        title = title,
        video = video == true,
        voteAverage = voteAverage,
        voteCount = voteCount,
    )