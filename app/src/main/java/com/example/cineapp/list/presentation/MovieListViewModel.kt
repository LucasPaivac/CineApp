package com.example.cineapp.list.presentation

import android.accounts.NetworkErrorException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.cineapp.common.data.RetrofitClient
import com.example.cineapp.list.data.ListService
import com.example.cineapp.list.data.ListRepository
import com.example.cineapp.list.presentation.ui.ListUiState
import com.example.cineapp.list.presentation.ui.MovieUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MovieListViewModel(
    private val repository: ListRepository
) : ViewModel() {

    private val _uiNowPlaying = MutableStateFlow(ListUiState())
    val uiNowPlaying: StateFlow<ListUiState> = _uiNowPlaying

    private val _uiMostPopular = MutableStateFlow(ListUiState())
    val uiMostPopular: StateFlow<ListUiState> = _uiMostPopular

    private val _uiTopRated = MutableStateFlow(ListUiState())
    val uiTopRated: StateFlow<ListUiState> = _uiTopRated

    private val _uiUpComing = MutableStateFlow(ListUiState())
    val uiUpComing: StateFlow<ListUiState> = _uiUpComing

    init {

        fetchNowPlayingMovies()
        fetchPopularMovies()
        fetchTopRatedMovies()
        fetchupComingMovies()

    }

    private fun fetchNowPlayingMovies() {
        _uiNowPlaying.value = ListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getNowPlaying()
            if (result.isSuccess) {
                val movies = result.getOrNull()?.results
                if (movies != null) {
                    val listUiData = movies.map { movieDto ->
                        MovieUiData(
                            id = movieDto.id,
                            title = movieDto.title,
                            overview = movieDto.overview,
                            image = movieDto.posterFullPath
                        )
                    }
                    _uiNowPlaying.value = ListUiState(list = listUiData)
                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiNowPlaying.value =
                        ListUiState(isError = true, errorMessage = "No internet connection")
                } else {
                    when (result.exceptionOrNull()) {
                        is UnknownHostException -> {
                            _uiNowPlaying.value =
                                ListUiState(isError = true, errorMessage = "No internet connection")
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
    }

    private fun fetchPopularMovies() {
        _uiMostPopular.value = ListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {

            val result = repository.getPopularMovies()
            if (result.isSuccess) {
                val movies = result.getOrNull()?.results
                if (movies != null) {
                    val listUiData = movies.map { movieDTO ->
                        MovieUiData(
                            id = movieDTO.id,
                            title = movieDTO.title,
                            overview = movieDTO.overview,
                            image = movieDTO.posterFullPath
                        )
                    }
                    _uiMostPopular.value = ListUiState(list = listUiData)
                }
            } else {
                when (result.exceptionOrNull()) {
                    is UnknownHostException -> {
                        _uiMostPopular.value =
                            ListUiState(isError = true, errorMessage = "No internet connection")
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
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getTopRatedMovies()
            if (result.isSuccess) {
                val movies = result.getOrNull()?.results
                if (movies != null) {
                    val listUiData = movies.map { movieDTO ->
                        MovieUiData(
                            id = movieDTO.id,
                            title = movieDTO.title,
                            overview = movieDTO.overview,
                            image = movieDTO.posterFullPath
                        )
                    }
                    _uiTopRated.value = ListUiState(list = listUiData)
                }
            } else {
                when (result.exceptionOrNull()) {
                    is UnknownHostException -> {
                        _uiTopRated.value =
                            ListUiState(isError = true, errorMessage = "No internet connection")
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

    private fun fetchupComingMovies() {
        _uiUpComing.value = ListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {

            val result = repository.getUpComingMovies()
            if(result.isSuccess){
                val movies = result.getOrNull()?.results
                if (movies != null){
                    val listUiData = movies.map { movieDTO ->
                        MovieUiData(
                            id = movieDTO.id,
                            title = movieDTO.title,
                            overview = movieDTO.overview,
                            image = movieDTO.posterFullPath
                        )
                    }
                    _uiUpComing.value = ListUiState(list = listUiData)
                }
            }else{
                when(result.exceptionOrNull()){
                    is UnknownHostException ->{
                        _uiUpComing.value = ListUiState(isError = true, errorMessage = "No connection")
                    }
                    is NetworkErrorException ->{
                        _uiUpComing.value = ListUiState(isError = true, errorMessage = "Server error")
                    }else -> {
                    _uiUpComing.value = ListUiState(isError = true)
                    }
                }
            }
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService = RetrofitClient.retrofit.create(ListService::class.java)
                val repository = ListRepository(listService = listService)
                return MovieListViewModel(
                    repository = repository
                ) as T
            }
        }
    }
}