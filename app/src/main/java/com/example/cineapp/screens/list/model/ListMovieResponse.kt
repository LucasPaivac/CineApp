package com.example.cineapp.screens.list.model

import com.example.cineapp.common.data.remote.model.ListMovieDTO

data class ListMovieResponse(
    val results: List<ListMovieDTO>
)