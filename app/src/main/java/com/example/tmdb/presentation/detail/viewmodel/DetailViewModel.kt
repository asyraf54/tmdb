package com.example.tmdb.presentation.detail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.base.Either
import com.example.tmdb.core.base.Failure
import com.example.tmdb.core.constant.RequestState
import com.example.tmdb.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class DetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state

    var isInitialized by mutableStateOf(false)
        private set

    fun initialize(id: Int) {
        if (!isInitialized) {
            getMovieDetail(id)
        }
    }

    fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            isInitialized = true
            _state.value = _state.value.copy(
                detailState = _state.value.detailState.copy(
                    requestState = RequestState.loading,
                )
            )

            val result = movieUseCase.getMovieDetail(id)
            when (result) {
                is Either.Left -> {
                    val failure = result.value

                    val requestState = when (failure) {
                        is Failure.NetworkFailure -> RequestState.noNetwork
                        else -> RequestState.error
                    }

                    _state.value = _state.value.copy(
                        detailState = _state.value.detailState.copy(
                            requestState = requestState,
                            message = failure.message
                        )
                    )
                }

                is Either.Right -> {
                    _state.value = _state.value.copy(
                        detailState = _state.value.detailState.copy(
                            requestState = RequestState.success,
                            data = result.value
                        )
                    )
                }
            }
        }
    }
}