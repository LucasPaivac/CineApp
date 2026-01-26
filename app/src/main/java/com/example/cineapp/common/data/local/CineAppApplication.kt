package com.example.cineapp.common.data.local

import android.app.Application
import androidx.room.Room
import com.example.cineapp.common.data.local.CineAppDataBase

class CineAppApplication: Application() {

    val db by lazy{
        Room.databaseBuilder(
            applicationContext,
            CineAppDataBase::class.java, "database-cine-app"
        ).build()
    }
}