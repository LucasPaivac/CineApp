package com.example.cineapp.list.data

import android.accounts.NetworkErrorException
import com.example.cineapp.common.model.MovieResponse
import com.example.cineapp.list.data.local.ListLocalDataSource
import com.example.cineapp.list.data.remote.ListRemoteDataSource
import com.example.cineapp.list.data.remote.ListService

class ListRepository(
    private val local: ListLocalDataSource,
    private val remote: ListRemoteDataSource
) {

    suspend fun getNowPlaying(): Result<MovieResponse?> {
        return Result.success(MovieResponse(emptyList()))
        /*return try {
            val response = listService.getNowPlayingMovies()
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }*/
    }

    suspend fun getPopularMovies(): Result<MovieResponse?>{
        return Result.success(MovieResponse(emptyList()))
        /*return try {
            val response = listService.getPopularMovies()
            if (response.isSuccessful){
                Result.success(response.body())
            }else {
                Result.failure(NetworkErrorException(response.message()))
            }
        }catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }*/
    }

    suspend fun getTopRatedMovies(): Result<MovieResponse?>{
        return Result.success(MovieResponse(emptyList()))
        /*return try {
            val response = listService.getTopRatedMovies()
            if (response.isSuccessful){
                Result.success(response.body())
            }else{
                Result.failure(NetworkErrorException(response.message()))
            }
        }catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }*/
    }

    suspend fun getUpComingMovies(): Result<MovieResponse?>{
        return Result.success(MovieResponse(emptyList()))
        /*return try {
            val response = listService.getUpComingMovies()
            if (response.isSuccessful){
                Result.success(response.body())
            }else{
                Result.failure(NetworkErrorException(response.message()))
            }
        }catch (ex: Exception){
            Result.failure(ex)
        }*/
    }
}