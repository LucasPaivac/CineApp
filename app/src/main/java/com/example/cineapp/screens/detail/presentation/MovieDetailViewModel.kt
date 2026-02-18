package com.example.cineapp.screens.detail.presentation

import android.accounts.NetworkErrorException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cineapp.common.repository.MovieRepository
import com.example.cineapp.common.utils.toDetailMovieUiData
import com.example.cineapp.di.annotations.DispatcherIO
import com.example.cineapp.screens.detail.model.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiMovieDetail = MutableStateFlow(DetailUiState())
    val uiMovieDetail: StateFlow<DetailUiState> = _uiMovieDetail

    fun fetchDetailMovie(movieId: String) {
        _uiMovieDetail.value = DetailUiState(isLoading = true)

        viewModelScope.launch(dispatcher) {
            val result = repository.getMovieDetail(movieId = movieId)

            if (result.isSuccess) {
                val movie = result.getOrNull()
                if(movie != null){
                    _uiMovieDetail.value = DetailUiState(movie = movie.toDetailMovieUiData())
                }

            } else {
                when(result.exceptionOrNull()){
                    is UnknownHostException -> {
                        _uiMovieDetail.value =
                            DetailUiState(isError = true, errorMessage = "No connection")
                    }
                    is NetworkErrorException -> {
                        _uiMovieDetail.value =
                            DetailUiState(isError = true, errorMessage = "Server Error")
                    }
                    else -> {
                        _uiMovieDetail.value = DetailUiState(isError = true)
                    }
                }
            }
        }
    }
}