package com.example.cineapp.common.data.service

import com.example.cineapp.common.data.remote.model.MovieDTO
import com.example.cineapp.common.data.remote.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("now_playing?language=en-US&page=1")
    suspend fun getNowPlayingMovies(): Response<MovieResponse>

    @GET("popular?language=en-US&page=1")
    suspend fun getPopularMovies(): Response<MovieResponse>

    @GET("top_rated?language=en-US&page=1")
    suspend fun getTopRatedMovies(): Response<MovieResponse>

    @GET("upcoming?language=en-US&page=1")
    suspend fun getUpComingMovies(): Response<MovieResponse>

    @GET("{movie_id}?language=en-US")
    suspend fun getMovieDetail(@Path("movie_id") movieId: String): Response<MovieDTO>
}