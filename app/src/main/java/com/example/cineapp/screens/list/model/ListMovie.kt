package com.example.cineapp.screens.list.model

data class ListMovie(
    val id: Int,
    val title: String,
    val overview: String,
    val imagePoster: String,
    val imageBanner: String,
    val category: String
)