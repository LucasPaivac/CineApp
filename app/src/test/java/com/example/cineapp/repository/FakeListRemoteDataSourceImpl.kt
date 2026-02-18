package com.example.cineapp.repository

import com.example.cineapp.common.data.remote.datasource.RemoteDataSource
import com.example.cineapp.common.data.remote.model.CreditsDTO
import com.example.cineapp.common.data.remote.model.DetailMovieDTO
import com.example.cineapp.common.data.remote.model.ListMovieDTO

class FakeListRemoteDataSourceImpl: RemoteDataSource {

    var nowPlaying = Result.success(emptyList<ListMovieDTO>())
    var popular = Result.success(emptyList<ListMovieDTO>())
    var topRated = Result.success(emptyList<ListMovieDTO>())
    var upComing = Result.success(emptyList<ListMovieDTO>())
    var movieDetail = Result.success(DetailMovieDTO(
        id = 0,
        title = "",
        overview = "",
        postPath = "",
        backdropPath = "",
        genres = emptyList(),
        releaseYear = "",
        runtime = 0,
        voteAverage = 0.0,
        cast = CreditsDTO(
            castList = emptyList()
        ),
    ))

    override suspend fun getNowPlayingMovies(): Result<List<ListMovieDTO>?> {
        return nowPlaying
    }

    override suspend fun getPopularMovies(): Result<List<ListMovieDTO>?> {
        return popular
    }

    override suspend fun getTopRatedMovies(): Result<List<ListMovieDTO>?> {
        return topRated
    }

    override suspend fun getUpComingMovies(): Result<List<ListMovieDTO>?> {
        return upComing
    }

    override suspend fun getMovieDetail(movieId: String): Result<DetailMovieDTO?> {
        return movieDetail
    }
}