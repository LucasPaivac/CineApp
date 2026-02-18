package com.example.cineapp.common.data.remote.datasource

import com.example.cineapp.common.data.remote.model.DetailMovieDTO
import com.example.cineapp.common.data.remote.model.ListMovieDTO

interface RemoteDataSource {

    suspend fun getNowPlayingMovies(): Result<List<ListMovieDTO>?>

    suspend fun getPopularMovies(): Result<List<ListMovieDTO>?>

    suspend fun getTopRatedMovies(): Result<List<ListMovieDTO>?>

    suspend fun getUpComingMovies(): Result<List<ListMovieDTO>?>

    suspend fun getMovieDetail(movieId: String): Result<DetailMovieDTO?>
}