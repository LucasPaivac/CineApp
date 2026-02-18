package com.example.cineapp.common.data.local.datasource

import com.example.cineapp.common.data.local.MovieCategory
import com.example.cineapp.common.data.local.MovieDao
import com.example.cineapp.common.data.local.model.MovieEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: MovieDao
): LocalDataSource {

    override suspend fun getNowPlayingMovies(): List<MovieEntity>{
        return dao.getMovieByCategory(MovieCategory.NowPlaying.name)
    }

    override suspend fun getPopularMovies(): List<MovieEntity>{
        return dao.getMovieByCategory(MovieCategory.Popular.name)
    }

    override suspend fun getTopRatedMovies(): List<MovieEntity>{
        return dao.getMovieByCategory(MovieCategory.TopRated.name)
    }

    override suspend fun getUpComingMovies(): List<MovieEntity>{
        return dao.getMovieByCategory(MovieCategory.Upcoming.name)
    }

    override suspend fun updateLocalMovies(movies: List<MovieEntity>){
        dao.insertAll(movies)
    }

}