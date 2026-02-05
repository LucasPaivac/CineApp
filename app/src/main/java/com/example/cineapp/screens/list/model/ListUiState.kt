package com.example.cineapp.screens.list.model

import com.example.cineapp.common.model.MovieUiData

data class ListUiState(
    val list: List<MovieUiData> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = "Something went wrong"
)


