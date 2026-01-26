package com.example.cineapp.list.data

import android.accounts.NetworkErrorException
import com.example.cineapp.common.model.MovieResponse

class ListRepository(
    private val listService: ListService
) {

    suspend fun getNowPlaying(): Result<MovieResponse?> {

        return try {
            val response = listService.getNowPlayingMovies()
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getPopularMovies(): Result<MovieResponse?>{

        return try {
            val response = listService.getPopularMovies()
            if (response.isSuccessful){
                Result.success(response.body())
            }else {
                Result.failure(NetworkErrorException(response.message()))
            }
        }catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getTopRatedMovies(): Result<MovieResponse?>{

        return try {
            val response = listService.getTopRatedMovies()
            if (response.isSuccessful){
                Result.success(response.body())
            }else{
                Result.failure(NetworkErrorException(response.message()))
            }
        }catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getUpComingMovies(): Result<MovieResponse?>{

        return try {
            val response = listService.getUpComingMovies()
            if (response.isSuccessful){
                Result.success(response.body())
            }else{
                Result.failure(NetworkErrorException(response.message()))
            }
        }catch (ex: Exception){
            Result.failure(ex)
        }
    }
}