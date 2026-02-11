package com.example.cineapp

import android.app.Application
import androidx.room.Room
import com.example.cineapp.common.data.local.CineAppDataBase
import com.example.cineapp.common.data.remote.RetrofitClient
import com.example.cineapp.common.utils.AndroidNetworkChecker
import com.example.cineapp.common.utils.NetworkChecker
import com.example.cineapp.common.repository.MovieRepository
import com.example.cineapp.common.data.local.datasource.LocalDataSourceImpl
import com.example.cineapp.common.data.local.datasource.LocalDataSource
import com.example.cineapp.common.data.remote.datasource.RemoteDataSource
import com.example.cineapp.common.data.remote.service.MovieService

class CineAppApplication: Application() {

    private val db by lazy{
        Room.databaseBuilder(
            applicationContext,
            CineAppDataBase::class.java, "database-cine-app"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    private val movieService by lazy{
        RetrofitClient.retrofit.create(MovieService::class.java)
    }

    private val localDataSource: LocalDataSource by lazy {
        LocalDataSourceImpl(dao = db.getMovieDao())
    }

    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSource(service = movieService)
    }

    val networkChecker: NetworkChecker by lazy {
        AndroidNetworkChecker(applicationContext)
    }

    val repository: MovieRepository by lazy {
        MovieRepository(
            local = localDataSource,
            remote = remoteDataSource
        )
    }
}