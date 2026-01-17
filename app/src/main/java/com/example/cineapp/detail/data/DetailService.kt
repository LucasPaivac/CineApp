package com.example.cineapp.detail.data

import com.example.cineapp.common.model.MovieDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {

    @GET("{movie_id}?language=en-US")
    fun getMovieDetail(@Path("movie_id") movieId: String): Call<MovieDTO>

}