package com.example.cineapp.common.data.local.datasource

import com.example.cineapp.common.data.local.model.MovieEntity

interface LocalDataSource {

    suspend fun getNowPlayingMovies(): List<MovieEntity>

    suspend fun getPopularMovies(): List<MovieEntity>

    suspend fun getTopRatedMovies(): List<MovieEntity>

    suspend fun getUpComingMovies(): List<MovieEntity>

    suspend fun updateLocalMovies(movies: List<MovieEntity>)
}