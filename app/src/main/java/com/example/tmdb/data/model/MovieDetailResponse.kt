package com.example.tmdb.data.model

import com.example.tmdb.domain.entity.Genre
import com.example.tmdb.domain.entity.MovieDetail
import com.example.tmdb.domain.entity.ProductionCompany
import com.example.tmdb.domain.entity.ProductionCountry
import com.example.tmdb.domain.entity.SpokenLanguage

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    val adult: Boolean? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("belongs_to_collection")
    val belongsToCollection: Any? = null,

    val budget: Int? = null,

    val genres: List<GenreResponse>? = null,

    val homepage: String? = null,
    val id: Int? = null,

    @SerializedName("imdb_id")
    val imdbId: String? = null,

    @SerializedName("origin_country")
    val originCountry: List<String>? = null,

    @SerializedName("original_language")
    val originalLanguage: String? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    val overview: String? = null,
    val popularity: Double? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyResponse>? = null,

    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountryResponse>? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    val revenue: Int? = null,
    val runtime: Int? = null,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageResponse>? = null,

    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,

    @SerializedName("vote_average")
    val voteAverage: Double? = null,

    @SerializedName("vote_count")
    val voteCount: Int? = null
) {
    fun toEntity(): MovieDetail {
        return MovieDetail(
            adult = adult == true,
            backdropPath = backdropPath.orEmpty(),
            belongsToCollection = belongsToCollection ?: Any(),
            budget = budget ?: 0,
            genres = genres?.map { Genre(it.id, it.name.orEmpty()) } ?: emptyList(),
            homepage = homepage.orEmpty(),
            id = id ?: 0,
            imdbId = imdbId.orEmpty(),
            originCountry = originCountry ?: emptyList(),
            originalLanguage = originalLanguage.orEmpty(),
            originalTitle = originalTitle.orEmpty(),
            overview = overview.orEmpty(),
            popularity = popularity ?: 0.0,
            posterPath = posterPath.orEmpty(),
            productionCompanies = productionCompanies?.map {
                ProductionCompany(
                    id = it.id,
                    logoPath = it.logoPath,
                    name = it.name.orEmpty(),
                    originCountry = it.originCountry.orEmpty()
                )
            } ?: emptyList(),
            productionCountries = productionCountries?.map {
                ProductionCountry(
                    iso31661 = it.iso31661.orEmpty(),
                    name = it.name.orEmpty()
                )
            } ?: emptyList(),
            releaseDate = releaseDate.orEmpty(),
            revenue = revenue ?: 0,
            runtime = runtime ?: 0,
            spokenLanguages = spokenLanguages?.map {
                SpokenLanguage(
                    englishName = it.englishName.orEmpty(),
                    iso6391 = it.iso6391.orEmpty(),
                    name = it.name.orEmpty()
                )
            } ?: emptyList(),
            status = status.orEmpty(),
            tagline = tagline.orEmpty(),
            title = title.orEmpty(),
            video = video == true,
            voteAverage = voteAverage ?: 0.0,
            voteCount = voteCount ?: 0
        )
    }
}
