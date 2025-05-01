package com.example.tmdb.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.base.BaseState
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
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUsecase
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _homeState


    fun getMovies(type: String, page: Int = 1) {
        viewModelScope.launch {
            _homeState.value = _homeState.value.copy(
                moviePopularState = _homeState.value.moviePopularState.copy(
                    requestState = RequestState.loading,
                )
            )

            val result = movieUseCase.getMovies(type, page)
            when (result) {
                is Either.Left -> {
                    val failure = result.value

                    val requestState = when (failure) {
                        is Failure.NetworkFailure -> RequestState.noNetwork
                        else -> RequestState.error
                    }

                    _homeState.value = _homeState.value.copy(
                        moviePopularState = _homeState.value.moviePopularState.copy(
                            requestState = requestState,
                            message = failure.message
                        )
                    )
                }

                is Either.Right -> {
                    _homeState.value = _homeState.value.copy(
                        moviesPopularPage = _homeState.value.moviesPopularPage + 1,
                        moviesPopular = result.value,
                        moviePopularState = _homeState.value.moviePopularState.copy(
                            requestState = RequestState.success,
                        )
                    )
                }
            }
        }
    }

    fun getNextMovies(type: String) {
        Log.d("AAA","A${_homeState.value.moviesPopularPage}")
        viewModelScope.launch {
            _homeState.value = _homeState.value.copy(
                movieNextPopularState = _homeState.value.movieNextPopularState.copy(
                    requestState = RequestState.loading,
                )
            )
            val result = movieUseCase.getMovies(type, _homeState.value.moviesPopularPage)
            when (result) {
                is Either.Left -> {
                    val failure = result.value
                    Log.d("AAA",failure.message)
                    val requestState = when (failure) {
                        is Failure.NetworkFailure -> RequestState.noNetwork
                        else -> RequestState.error
                    }

                    _homeState.value = _homeState.value.copy(
                        movieNextPopularState = _homeState.value.movieNextPopularState.copy(
                            requestState = requestState,
                            message = failure.message
                        )
                    )
                }

                is Either.Right -> {
                    Log.d("AAA","success")
                    _homeState.value = _homeState.value.copy(
                        moviesPopularPage = _homeState.value.moviesPopularPage + 1,
                        moviesPopular =  _homeState.value.moviesPopular.plus(result.value),
                        movieNextPopularState = _homeState.value.movieNextPopularState.copy(
                            requestState = RequestState.success,
                        )
                    )
                }
            }
        }
    }

}
