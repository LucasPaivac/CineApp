package com.example.cineapp.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cineapp.common.data.local.model.MovieEntity

@Database([MovieEntity::class], version = 1)
abstract class CineAppDataBase: RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

}