package com.example.cineapp.common.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val overview: String,
    val image: String,
    @ColumnInfo(name = "category")
    val category: String
)
