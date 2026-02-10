package com.example.cineapp.screens.list

import android.accounts.NetworkErrorException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.cineapp.CineAppApplication
import com.example.cineapp.common.model.MovieUiData
import com.example.cineapp.common.repository.MovieRepository
import com.example.cineapp.common.utils.NetworkChecker
import com.example.cineapp.common.utils.toMovieUiData
import com.example.cineapp.screens.list.model.ListUiState
import com.example.cineapp.screens.list.model.MovieListUiEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MovieListViewModel(
    private val repository: MovieRepository,
    private val networkChecker: NetworkChecker,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<MovieListUiEvent>(
        replay = 0,
        extraBufferCapacity = 0
    )
    val uiEvent: SharedFlow<MovieListUiEvent> = _uiEvent

    private val _uiNowPlaying = MutableStateFlow(ListUiState())
    val uiNowPlaying: StateFlow<ListUiState> = _uiNowPlaying

    private val _uiMostPopular = MutableStateFlow(ListUiState())
    val uiMostPopular: StateFlow<ListUiState> = _uiMostPopular

    private val _uiTopRated = MutableStateFlow(ListUiState())
    val uiTopRated: StateFlow<ListUiState> = _uiTopRated

    private val _uiUpComing = MutableStateFlow(ListUiState())
    val uiUpComing: StateFlow<ListUiState> = _uiUpComing

    private var isSnackbarShowing = false

    init {

        fetchNowPlayingMovies()
        fetchPopularMovies()
        fetchTopRatedMovies()
        fetchUpcomingMovies()

    }
    private fun fetchNowPlayingMovies() {
        _uiNowPlaying.value = ListUiState(isLoading = true)
        viewModelScope.launch(dispatcher) {
            val result = repository.getNowPlayingMovies()
            if (result.isSuccess) {
                val movies = result.getOrNull()
                if (movies != null) {
                    val listUiData = movies.map {
                        it.toMovieUiData()
                    }
                    _uiNowPlaying.value = ListUiState(list = listUiData)
                }
            } else {
                when (result.exceptionOrNull()) {
                    is UnknownHostException -> {
                        _uiNowPlaying.value =
                            ListUiState(isError = true, errorMessage = "No connection")
                    }

                    is NetworkErrorException -> {
                        _uiNowPlaying.value =
                            ListUiState(isError = true, errorMessage = "Server Error")
                    }

                    else -> {
                        _uiNowPlaying.value = ListUiState(isError = true)
                    }
                }
            }
        }
    }

    private fun fetchPopularMovies() {
        _uiMostPopular.value = ListUiState(isLoading = true)
        viewModelScope.launch(dispatcher) {

            val result = repository.getPopularMovies()
            if (result.isSuccess) {
                val movies = result.getOrNull()
                if (movies != null) {
                    val listUiData = movies.map {
                        it.toMovieUiData()
                    }
                    _uiMostPopular.value = ListUiState(list = listUiData)
                }
            } else {
                when (result.exceptionOrNull()) {
                    is UnknownHostException -> {
                        _uiMostPopular.value =
                            ListUiState(isError = true, errorMessage = "No connection")
                    }

                    is NetworkErrorException -> {
                        _uiMostPopular.value =
                            ListUiState(isError = true, errorMessage = "Server Error")
                    }

                    else -> {
                        _uiMostPopular.value = ListUiState(isError = true)
                    }
                }
            }
        }
    }

    private fun fetchTopRatedMovies() {
        _uiTopRated.value = ListUiState(isLoading = true)
        viewModelScope.launch(dispatcher) {

            val result = repository.getTopRatedMovies()
            if (result.isSuccess) {
                val movies = result.getOrNull()
                if (movies != null) {
                    val listUiData = movies.map {
                        it.toMovieUiData()
                    }
                    _uiTopRated.value = ListUiState(list = listUiData)
                }
            } else {
                when (result.exceptionOrNull()) {
                    is UnknownHostException -> {
                        _uiTopRated.value =
                            ListUiState(isError = true, errorMessage = "No connection")
                    }

                    is NetworkErrorException -> {
                        _uiTopRated.value =
                            ListUiState(isError = true, errorMessage = "Server Error")
                    }

                    else -> {
                        _uiTopRated.value = ListUiState(isError = true)
                    }
                }
            }
        }

    }

    private fun fetchUpcomingMovies() {
        _uiUpComing.value = ListUiState(isLoading = true)
        viewModelScope.launch(dispatcher) {
            val result = repository.getUpComingMovies()
            if (result.isSuccess) {
                val movies = result.getOrNull()
                if (movies != null) {
                    val listUiData = movies.map {
                        it.toMovieUiData()
                    }
                    _uiUpComing.value = ListUiState(list = listUiData)
                }
            } else {
                when (result.exceptionOrNull()) {
                    is UnknownHostException -> {
                        _uiUpComing.value =
                            ListUiState(isError = true, errorMessage = "No connection")
                    }

                    is NetworkErrorException -> {
                        _uiUpComing.value =
                            ListUiState(isError = true, errorMessage = "Server Error")
                    }

                    else -> {
                        _uiUpComing.value = ListUiState(isError = true)
                    }
                }
            }
        }

    }

    fun onMovieClicked(movieId: Int){
        viewModelScope.launch {
            if (networkChecker.hasInternet()){
                _uiEvent.emit(
                    MovieListUiEvent.NavigateToDetails(movieId)
                )
            }else{
                if (!isSnackbarShowing){
                    isSnackbarShowing = true
                    _uiEvent.emit(
                        MovieListUiEvent.ShowMessage("No internet connection")
                    )
                }
            }
        }
    }

    fun onSnackbarDismissed() {
        isSnackbarShowing = false
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY]) as CineAppApplication
                return MovieListViewModel(
                    repository = application.repository,
                    networkChecker = application.networkChecker
                ) as T
            }
        }
    }
}