package com.example.tmdb.core.constant

enum class MovieType{
    popular, nowPlaying, topRated, upcoming;

    companion object {
        fun getFromType(type: String): MovieType = when (type) {
            "popular" -> popular
            "now_playing" -> nowPlaying
            "top_rated" -> topRated
            "upcoming" -> upcoming
            else -> popular // default fallback
        }
    }


    fun displayName(): String = when (this) {
        popular -> "Popular"
        nowPlaying -> "Now Playing"
        topRated -> "Top Rated"
        upcoming -> "Upcoming"
    }

    fun paramKey(): String = when (this) {
        popular -> "popular"
        nowPlaying -> "now_playing"
        topRated -> "top_rated"
        upcoming -> "upcoming"
    }
}

