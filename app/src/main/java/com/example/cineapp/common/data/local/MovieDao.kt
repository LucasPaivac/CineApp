package com.example.cineapp.common.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("Select * From movieentity where category is :category")
    suspend fun getMovieByCategory(category: String):List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(moviesEntity: List<MovieEntity>)
}