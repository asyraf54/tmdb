package com.example.tmdb.presentation.favorite.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.base.Either
import com.example.tmdb.core.base.Failure
import com.example.tmdb.core.constant.RequestState
import com.example.tmdb.domain.entity.MovieDetail
import com.example.tmdb.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class FavoriteViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(FavoriteState())
    val state: StateFlow<FavoriteState> = _state

    fun setIsFavorite(isFavorite: Boolean){
        _state.value = _state.value.copy(
            isFavorite = isFavorite
        )
    }

    fun isFavoriteMovie(movieId: Int) {
        viewModelScope.launch {

            val result = movieUseCase.isFavoriteMovie(movieId)
            when (result) {
                is Either.Left -> {

                }

                is Either.Right -> {
                    _state.value = _state.value.copy(
                        isFavorite = result.value
                    )
                }
            }
        }
    }

    fun getAllFavoriteMovie() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                getAllFavoriteMovieState = _state.value.getAllFavoriteMovieState.copy(
                    requestState = RequestState.loading,
                )
            )

            val result = movieUseCase.getAllFavoriteMovie()
            when (result) {
                is Either.Left -> {
                    val failure = result.value

                    val requestState = when (failure) {
                        is Failure.NetworkFailure -> RequestState.noNetwork
                        else -> RequestState.error
                    }

                    _state.value = _state.value.copy(
                        getAllFavoriteMovieState = _state.value.getAllFavoriteMovieState.copy(
                            requestState = requestState,
                            message = failure.message
                        )
                    )
                }

                is Either.Right -> {
                    _state.value = _state.value.copy(
                        getAllFavoriteMovieState = _state.value.getAllFavoriteMovieState.copy(
                            requestState = RequestState.success,
                            data = result.value
                        )
                    )
                }
            }
        }
    }

    fun insertFavoriteMovie(movie: MovieDetail) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                insertFavoriteMovieState = _state.value.insertFavoriteMovieState.copy(
                    requestState = RequestState.loading,
                )
            )

            val result = movieUseCase.insertFavoriteMovie(movie)
            when (result) {
                is Either.Left -> {
                    val failure = result.value

                    val requestState = when (failure) {
                        is Failure.NetworkFailure -> RequestState.noNetwork
                        else -> RequestState.error
                    }

                    _state.value = _state.value.copy(
                        insertFavoriteMovieState = _state.value.insertFavoriteMovieState.copy(
                            requestState = requestState,
                            message = failure.message
                        )
                    )
                }

                is Either.Right -> {
                    _state.value = _state.value.copy(
                        insertFavoriteMovieState = _state.value.insertFavoriteMovieState.copy(
                            requestState = RequestState.success,
                            data = result.value
                        )
                    )
                }
            }
        }
    }

    fun deleteFavoriteMovie(movie: MovieDetail) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                deleteFavoriteMovieState = _state.value.deleteFavoriteMovieState.copy(
                    requestState = RequestState.loading,
                )
            )

            val result = movieUseCase.deleteFavoriteMovie(movie)
            when (result) {
                is Either.Left -> {
                    val failure = result.value

                    val requestState = when (failure) {
                        is Failure.NetworkFailure -> RequestState.noNetwork
                        else -> RequestState.error
                    }

                    _state.value = _state.value.copy(
                        deleteFavoriteMovieState = _state.value.deleteFavoriteMovieState.copy(
                            requestState = requestState,
                            message = failure.message
                        )
                    )
                }

                is Either.Right -> {
                    _state.value = _state.value.copy(
                        deleteFavoriteMovieState = _state.value.deleteFavoriteMovieState.copy(
                            requestState = RequestState.success,
                            data = result.value
                        )
                    )
                }
            }
        }
    }
}