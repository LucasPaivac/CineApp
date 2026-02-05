package com.example.cineapp.screens.detail

import android.accounts.NetworkErrorException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.cineapp.CineAppApplication
import com.example.cineapp.common.repository.MovieRepository
import com.example.cineapp.common.utils.toMovieUiData
import com.example.cineapp.screens.detail.model.DetailUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MovieDetailViewModel(
    private val repository: MovieRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
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
                    _uiMovieDetail.value = DetailUiState(movie = movie.toMovieUiData())
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

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY]) as CineAppApplication
                return MovieDetailViewModel(
                    repository = application.repository
                ) as T
            }
        }

    }
}