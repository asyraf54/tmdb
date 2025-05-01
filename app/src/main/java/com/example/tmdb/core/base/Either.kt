package com.example.tmdb.core.base

sealed class Either<out L, out R> {
    data class Left<out L>(val value: L) : Either<L, Nothing>()
    data class Right<out R>(val value: R) : Either<Nothing, R>()

    fun isRight() = this is Right<R>
    fun isLeft() = this is Left<L>
}
