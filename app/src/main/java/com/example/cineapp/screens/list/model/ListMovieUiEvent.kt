package com.example.cineapp.screens.list.model

sealed class ListMovieUiEvent {
    data class ShowMessage(val message: String) : ListMovieUiEvent()
    data class NavigateToDetails(val movieId: Int) : ListMovieUiEvent()
}