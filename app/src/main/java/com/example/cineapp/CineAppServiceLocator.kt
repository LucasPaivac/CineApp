package com.example.cineapp

import android.app.Application
import androidx.room.Room
import com.example.cineapp.common.data.local.CineAppDataBase
import com.example.cineapp.common.data.local.datasource.LocalDataSource
import com.example.cineapp.common.data.local.datasource.LocalDataSourceImpl
import com.example.cineapp.common.data.remote.RetrofitClient
import com.example.cineapp.common.data.remote.datasource.RemoteDataSource
import com.example.cineapp.common.data.remote.datasource.RemoteDataSourceImpl
import com.example.cineapp.common.data.remote.service.MovieService
import com.example.cineapp.common.repository.MovieRepository

object CineAppServiceLocator {

    fun getRepository(application: Application): MovieRepository {
        val db = Room.databaseBuilder(
            application,
            CineAppDataBase::class.java, "database-cine-app"
        )
            .fallbackToDestructiveMigration(true)
            .build()

        val movieService =
            RetrofitClient.retrofit.create(MovieService::class.java)

        val localDataSource: LocalDataSource =
            LocalDataSourceImpl(dao = db.getMovieDao())

        val remoteDataSource: RemoteDataSource =
            RemoteDataSourceImpl(service = movieService)

        return MovieRepository(
            local = localDataSource,
            remote = remoteDataSource
        )

    }


}