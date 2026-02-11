package com.example.cineapp.screens.detail.model

data class DetailUiState(
    val movie: DetailMovieUiData = DetailMovieUiData(
        id = 0,
        title = "",
        overview = "",
        imagePoster = "",
        imageBanner = "",
        genre = "",
        releaseYear = "",
        runtime = "",
        voteAverage = "",
        cast = emptyList()
    ),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = "Something went wrong"
)
