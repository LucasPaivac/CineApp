package com.example.cineapp.screens.detail.model

data class DetailMovieUiData(
    val id: Int,
    val title: String,
    val overview: String,
    val imagePoster: String,
    val imageBanner: String,
    val genre: String,
    val releaseYear: String,
    val runtime: String,
    val voteAverage: String,
    val cast: List<CastUiData>
)

data class CastUiData(
    val id: Int,
    val name: String,
    val character: String,
    val profileImage: String
)
