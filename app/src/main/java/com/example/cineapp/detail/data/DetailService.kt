package com.example.cineapp.detail.data

import com.example.cineapp.common.model.MovieDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {

    @GET("{movie_id}?language=en-US")
    suspend fun getMovieDetail(@Path("movie_id") movieId: String): Response<MovieDTO>

}