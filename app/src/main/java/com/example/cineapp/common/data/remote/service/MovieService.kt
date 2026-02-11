package com.example.cineapp.common.data.remote.service

import com.example.cineapp.common.data.remote.model.DetailMovieDTO
import com.example.cineapp.common.data.remote.model.ListMovieDTO
import com.example.cineapp.screens.list.model.ListMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("now_playing?language=en-US&page=1")
    suspend fun getNowPlayingMovies(): Response<ListMovieResponse>

    @GET("popular?language=en-US&page=1")
    suspend fun getPopularMovies(): Response<ListMovieResponse>

    @GET("top_rated?language=en-US&page=1")
    suspend fun getTopRatedMovies(): Response<ListMovieResponse>

    @GET("upcoming?language=en-US&page=1")
    suspend fun getUpComingMovies(): Response<ListMovieResponse>

    @GET("{movie_id}?language=en-US")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query("append_to_response") append: String = "credits"): Response<DetailMovieDTO>

}