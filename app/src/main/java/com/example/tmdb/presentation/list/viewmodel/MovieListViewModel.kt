package com.example.tmdb.presentation.list.viewmodel

import com.example.tmdb.core.constant.MovieType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.base.Either
import com.example.tmdb.core.base.Failure
import com.example.tmdb.core.constant.RequestState
import com.example.tmdb.domain.usecase.MovieUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieUseCase: MovieUsecase
) : ViewModel() {
    private val _state = MutableStateFlow(MovieListState())
    val state: StateFlow<MovieListState> = _state


    fun getMovies(movieType: MovieType, page: Int = 1) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                moviesState = _state.value.moviesState.copy(
                    requestState = RequestState.loading,
                )
            )

            val result = movieUseCase.getMovies(movieType.paramKey(), page)
            when (result) {
                is Either.Left -> {
                    val failure = result.value

                    val requestState = when (failure) {
                        is Failure.NetworkFailure -> RequestState.noNetwork
                        else -> RequestState.error
                    }

                    _state.value = _state.value.copy(
                        moviesState = _state.value.moviesState.copy(
                            requestState = requestState,
                            message = failure.message
                        )
                    )
                }

                is Either.Right -> {
                    _state.value = _state.value.copy(
                        moviePage = _state.value.moviePage + 1,
                        movies = result.value,
                        moviesState = _state.value.moviesState.copy(
                            requestState = RequestState.success,
                        )
                    )
                }
            }
        }
    }

    fun getNextMovies(movieType: MovieType) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                moviesNextState = _state.value.moviesNextState.copy(
                    requestState = RequestState.loading,
                )
            )
            val result = movieUseCase.getMovies(movieType.paramKey(), _state.value.moviePage)
            when (result) {
                is Either.Left -> {
                    val failure = result.value
                    val requestState = when (failure) {
                        is Failure.NetworkFailure -> RequestState.noNetwork
                        else -> RequestState.error
                    }

                    _state.value = _state.value.copy(
                        moviesNextState = _state.value.moviesNextState.copy(
                            requestState = requestState,
                            message = failure.message
                        )
                    )
                }

                is Either.Right -> {
                    _state.value = _state.value.copy(
                        moviePage = _state.value.moviePage + 1,
                        movies =  _state.value.movies.plus(result.value),
                        moviesNextState = _state.value.moviesNextState.copy(
                            requestState = RequestState.success,
                        )
                    )
                }
            }
        }
    }
}
