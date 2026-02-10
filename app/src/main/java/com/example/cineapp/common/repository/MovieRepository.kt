package com.example.cineapp.common.repository

import android.util.Log
import com.example.cineapp.common.data.local.MovieCategory
import com.example.cineapp.common.model.MovieList
import com.example.cineapp.common.data.local.datasource.LocalDataSource
import com.example.cineapp.common.data.remote.datasource.RemoteDataSource
import com.example.cineapp.common.model.MovieDetail
import com.example.cineapp.common.utils.toEntity
import com.example.cineapp.common.utils.toMovieDetail
import com.example.cineapp.common.utils.toMovieList

class MovieRepository(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) {

    //Screen - List
    suspend fun getNowPlayingMovies(): Result<List<MovieList>?> {

        return try {
            val result = remote.getNowPlayingMovies()

            if (result.isSuccess) {
                val moviesRemote = result.getOrNull() ?: emptyList()

                if (moviesRemote.isNotEmpty()) {
                    local.updateLocalMovies(
                        moviesRemote.map {
                            it.toEntity(
                                MovieCategory.NowPlaying
                            )
                        }
                    )
                }

                Result.success(local.getNowPlayingMovies().map { it.toMovieList() })

            } else {
                val moviesLocal = local.getNowPlayingMovies()

                if (moviesLocal.isEmpty()) {
                    val exception = result.exceptionOrNull() ?: Exception("Unknown Remote Error")
                    Result.failure(exception)
                } else {
                    val moviesDomain = moviesLocal.map { it.toMovieList() }
                    Result.success(moviesDomain)
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }

    }

    suspend fun getPopularMovies(): Result<List<MovieList>?> {

        return try {
            val result = remote.getPopularMovies()

            if (result.isSuccess) {
                val remoteMovies = result.getOrNull() ?: emptyList()

                if (remoteMovies.isNotEmpty()) {
                    local.updateLocalMovies(
                        remoteMovies.map {
                            it.toEntity(
                                MovieCategory.Popular
                            )
                        }
                    )
                }

                Result.success(local.getPopularMovies().map { it.toMovieList() })
            } else {
                val localMovies = local.getPopularMovies()
                if (localMovies.isEmpty()) {
                    val exception = result.exceptionOrNull() ?: Exception("Unknown Remote Error")
                    Result.failure(exception)
                } else {
                    Result.success(localMovies.map { it.toMovieList() })
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getTopRatedMovies(): Result<List<MovieList>?> {

        return try {
            val result = remote.getTopRatedMovies()
            if (result.isSuccess) {
                val remoteMovies = result.getOrNull() ?: emptyList()
                if (remoteMovies.isNotEmpty()) {
                    local.updateLocalMovies(
                        remoteMovies.map {
                            it.toEntity(
                                MovieCategory.TopRated
                            )
                        }
                    )
                }

                Result.success(local.getTopRatedMovies().map { it.toMovieList() })
            } else {
                val localMovies = local.getTopRatedMovies()
                if (localMovies.isEmpty()) {
                    val exception = result.exceptionOrNull() ?: Exception("Unknown Remote Error")
                    Result.failure(exception)
                } else {
                    Result.success(localMovies.map { it.toMovieList() })
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getUpComingMovies(): Result<List<MovieList>?> {

        return try {
            val result = remote.getUpComingMovies()
            if (result.isSuccess) {
                val remoteMovies = result.getOrNull() ?: emptyList()
                if (remoteMovies.isNotEmpty()) {
                    local.updateLocalMovies(
                        remoteMovies.map {
                            it.toEntity(
                                MovieCategory.Upcoming
                            )
                        }
                    )
                }
                Result.success(local.getUpComingMovies().map { it.toMovieList() })
            } else {
                val localMovies = local.getUpComingMovies()
                if (localMovies.isEmpty()) {
                    val exception = result.exceptionOrNull() ?: Exception("Unknown Remote Error")
                    Result.failure(exception)
                } else {
                    Result.success(localMovies.map { it.toMovieList() })
                }
            }
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    //Screen - Detail
    suspend fun getMovieDetail(movieId: String): Result<MovieDetail?> {

        return try {
            val result = remote.getMovieDetail(movieId)
            if (result.isSuccess){
                val remoteMovie = result.getOrNull()
                Result.success(remoteMovie?.toMovieDetail())
            }else{
                val exception = result.exceptionOrNull() ?: Exception("Unknown Remote Error")
                Result.failure(exception)
            }
        } catch (ex: Exception){
            Result.failure(ex)
        }
    }
}