package com.example.cineapp.list.presentation.ui

data class ListUiState(
    val list: List<MovieUiData> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = "Something went wrong"
)

data class MovieUiData(
    val id: Int,
    val title: String,
    val overview: String,
    val image: String
)
