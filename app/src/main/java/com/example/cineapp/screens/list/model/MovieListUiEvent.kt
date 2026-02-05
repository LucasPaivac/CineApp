package com.example.cineapp.screens.list.model

sealed class MovieListUiEvent {
    data class ShowMessage(val message: String) : MovieListUiEvent()
    data class NavigateToDetails(val movieId: Int) : MovieListUiEvent()
}