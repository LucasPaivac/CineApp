package com.example.cineapp.repository

import com.example.cineapp.common.data.local.datasource.LocalDataSource
import com.example.cineapp.common.data.local.model.MovieEntity

class FakeListLocalDataSourceImpl: LocalDataSource {

    var nowPlaying = emptyList<MovieEntity>()
    var popular = emptyList<MovieEntity>()
    var topRated = emptyList<MovieEntity>()
    var upComing = emptyList<MovieEntity>()
    var updatedMovieLists = emptyList<MovieEntity>()

    override suspend fun getNowPlayingMovies(): List<MovieEntity> {
        return nowPlaying
    }

    override suspend fun getPopularMovies(): List<MovieEntity> {
        return popular
    }

    override suspend fun getTopRatedMovies(): List<MovieEntity> {
        return topRated
    }

    override suspend fun getUpComingMovies(): List<MovieEntity> {
        return upComing
    }

    override suspend fun updateLocalMovies(movies: List<MovieEntity>) {
        updatedMovieLists = movies
    }
}