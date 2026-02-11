package com.example.cineapp.screens.list.model

import com.example.cineapp.screens.list.model.ListMovieUiData

data class ListUiState(
    val list: List<ListMovieUiData> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = "Something went wrong"
)


