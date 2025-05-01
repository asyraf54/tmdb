package com.example.tmdb.core.utils.mapper


import com.example.tmdb.core.base.Failure
import retrofit2.HttpException
import java.io.IOException

fun mapExceptionToFailure(e: Throwable): Failure = when (e) {
    is IOException -> Failure.NetworkFailure
    is HttpException -> Failure.ServerFailure(e.message ?: "Server error")
    else -> Failure.UnknownFailure(e.message ?: "Something Went Wrong")
}