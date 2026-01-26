package com.example.cineapp.common.data.local

import android.app.Application
import androidx.room.Room
import com.example.cineapp.common.data.RetrofitClient
import com.example.cineapp.list.data.ListRepository
import com.example.cineapp.list.data.local.ListLocalDataSource
import com.example.cineapp.list.data.remote.ListRemoteDataSource
import com.example.cineapp.list.data.remote.ListService


class CineAppApplication: Application() {

    private val db by lazy{
        Room.databaseBuilder(
            applicationContext,
            CineAppDataBase::class.java, "database-cine-app"
        ).build()
    }

    private val listService by lazy{
        RetrofitClient.retrofit.create(ListService::class.java)
    }

    private val localDataSource: ListLocalDataSource by lazy {
        ListLocalDataSource(dao = db.getMovieDao())
    }

    private val remoteDataSource: ListRemoteDataSource by lazy {
        ListRemoteDataSource(service = listService)
    }

    val repository: ListRepository by lazy {
        ListRepository(
            local = localDataSource,
            remote = remoteDataSource
        )
    }
}