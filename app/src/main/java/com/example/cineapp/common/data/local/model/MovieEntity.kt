package com.example.cineapp.common.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val imagePoster: String,
    val imageBanner: String,
    @ColumnInfo(name = "category")
    val category: String,
)