package com.example.cineapp.common.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieDTO(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val postPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String
){
    val posterFullPath: String
        get() = "https://image.tmdb.org/t/p/w300/$postPath"

    val backdropFullPath: String
        get() = "https://image.tmdb.org/t/p/w780/$backdropPath"
}
