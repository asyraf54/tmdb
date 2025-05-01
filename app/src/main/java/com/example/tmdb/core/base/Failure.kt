package com.example.tmdb.core.base

sealed class Failure(open val message: String) {
    object NetworkFailure : Failure("No internet connection")
    data class ServerFailure(override val message: String) : Failure(message)
    data class UnknownFailure(override val message: String) : Failure("Something went wrong")
}
