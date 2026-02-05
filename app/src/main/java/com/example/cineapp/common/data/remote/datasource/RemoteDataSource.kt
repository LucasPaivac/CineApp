package com.example.cineapp.common.data.remote.datasource

import android.accounts.NetworkErrorException
import com.example.cineapp.common.data.remote.model.MovieDTO
import com.example.cineapp.common.data.service.MovieService

class RemoteDataSource(
    private val service: MovieService
) {
    suspend fun getNowPlayingMovies(): Result<List<MovieDTO>?> {

        return try {
            val response = service.getNowPlayingMovies()
            if (response.isSuccessful) {
                Result.success(
                    response.body()?.results
                )
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getPopularMovies(): Result<List<MovieDTO>?> {
        return try {
            val response = service.getPopularMovies()
            if (response.isSuccessful) {
                Result.success(
                    response.body()?.results
                )
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getTopRatedMovies(): Result<List<MovieDTO>?> {

        return try {
            val response = service.getTopRatedMovies()
            if (response.isSuccessful) {
                Result.success(
                    response.body()?.results
                )
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getUpComingMovies(): Result<List<MovieDTO>?> {

        return try {
            val response = service.getUpComingMovies()
            if (response.isSuccessful) {
                Result.success(
                    response.body()?.results
                )
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    suspend fun getMovieDetail(movieId: String): Result<MovieDTO?>{
         return try {
             val response = service.getMovieDetail(movieId)
             if (response.isSuccessful){
                 Result.success(
                     response.body()
                 )
             }else{
                 Result.failure(NetworkErrorException(response.message()))
             }
         }catch (ex: Exception){
             Result.failure(ex)
         }
    }
}