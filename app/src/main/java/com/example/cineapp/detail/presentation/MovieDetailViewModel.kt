package com.example.cineapp.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.cineapp.common.data.RetrofitClient
import com.example.cineapp.common.model.MovieDTO
import com.example.cineapp.detail.data.DetailService
import com.example.cineapp.list.data.ListService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel(
    private val detailService: DetailService,
): ViewModel() {

    private val _uiMovieDto = MutableStateFlow<MovieDTO?>(null)
    val uiMovieDto: StateFlow<MovieDTO?> = _uiMovieDto

    fun fetchDetailMovie(movieId: String){
        if (_uiMovieDto.value == null){
            viewModelScope.launch(Dispatchers.IO) {
                val response = detailService.getMovieDetail(movieId = movieId)
                if (response.isSuccessful){
                    _uiMovieDto.value = response.body()
                }else{
                    Log.d("MovieDetailViewModel", "Request error: ${response.errorBody()}")
                }
            }
        }
    }

    fun cleanMovieId(){
        viewModelScope.launch {
            delay(1000)
            _uiMovieDto.value = null
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val detailService = RetrofitClient.retrofit.create(DetailService::class.java)
                return MovieDetailViewModel(
                    detailService = detailService
                ) as T
            }
        }

    }
}