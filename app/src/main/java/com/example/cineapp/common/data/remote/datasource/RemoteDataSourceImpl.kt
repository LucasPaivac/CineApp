package com.example.cineapp.common.data.remote.datasource

import android.accounts.NetworkErrorException
import com.example.cineapp.common.data.remote.model.DetailMovieDTO
import com.example.cineapp.common.data.remote.model.ListMovieDTO
import com.example.cineapp.common.data.remote.service.MovieService
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val service: MovieService
): RemoteDataSource {
    override suspend fun getNowPlayingMovies(): Result<List<ListMovieDTO>?> {

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

    override suspend fun getPopularMovies(): Result<List<ListMovieDTO>?> {
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

    override suspend fun getTopRatedMovies(): Result<List<ListMovieDTO>?> {

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

    override suspend fun getUpComingMovies(): Result<List<ListMovieDTO>?> {

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

    override suspend fun getMovieDetail(movieId: String): Result<DetailMovieDTO?>{
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