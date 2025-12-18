package com.example.cineapp

import retrofit2.Call
import retrofit2.http.GET

interface ApiMovieService {

    @GET("now_playing?language=en-US&page=1")
    fun getNowPlayingMovies(): Call<MovieResponse>

    @GET("popular?language=en-US&page=1")
    fun getPopularMovies(): Call<MovieResponse>

}

