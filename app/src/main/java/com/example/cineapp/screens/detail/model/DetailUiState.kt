package com.example.cineapp.screens.detail.model

import com.example.cineapp.common.model.MovieUiData

data class DetailUiState(
    val movie: MovieUiData = MovieUiData(
        id = 0,
        title = "",
        overview = "",
        image = "",
    ),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = "Something went wrong"
)
