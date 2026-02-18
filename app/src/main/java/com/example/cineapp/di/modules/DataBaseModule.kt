package com.example.cineapp.di.modules

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cineapp.common.data.local.CineAppDataBase
import com.example.cineapp.common.data.local.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    fun providesCineAppDataBase(application: Application): CineAppDataBase {
        return Room.databaseBuilder(
            application.applicationContext,
            CineAppDataBase::class.java,
            "database-cine-app"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun providesDao(roomDatabase: CineAppDataBase): MovieDao {
        return roomDatabase.getMovieDao()
    }

}