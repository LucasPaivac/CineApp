package com.example.cineapp.list

import com.example.cineapp.common.model.MovieList
import com.example.cineapp.common.data.local.datasource.LocalDataSource

class FakeListLocalDataSourceImpl: LocalDataSource {

    var nowPlaying = emptyList<MovieList>()
    var popular = emptyList<MovieList>()
    var topRated = emptyList<MovieList>()
    var upComing = emptyList<MovieList>()
    var updatedMovieLists = emptyList<MovieList>()

    override suspend fun getNowPlayingMovies(): List<MovieList> {
        return nowPlaying
    }

    override suspend fun getPopularMovies(): List<MovieList> {
        return popular
    }

    override suspend fun getTopRatedMovies(): List<MovieList> {
        return topRated
    }

    override suspend fun getUpComingMovies(): List<MovieList> {
        return upComing
    }

    override suspend fun updateLocalMovies(movieLists: List<MovieList>) {
        updatedMovieLists = movieLists
    }
}