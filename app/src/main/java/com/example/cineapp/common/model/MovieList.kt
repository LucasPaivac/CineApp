package com.example.cineapp.common.model

data class MovieList(
    val id: Int,
    val title: String,
    val overview: String,
    val imagePoster: String,
    val imageBanner: String,
    val category: String
)