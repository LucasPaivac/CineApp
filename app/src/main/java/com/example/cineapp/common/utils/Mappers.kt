package com.example.cineapp.common.utils

import com.example.cineapp.common.data.local.MovieCategory
import com.example.cineapp.common.data.local.model.MovieEntity
import com.example.cineapp.common.data.remote.model.MovieDTO
import com.example.cineapp.common.model.MovieDetail
import com.example.cineapp.common.model.MovieList
import com.example.cineapp.common.model.MovieUiData

fun MovieDTO.toMovieList(category: MovieCategory) = MovieList(
    id = id,
    title = title,
    overview = overview,
    imagePoster = posterFullPath,
    imageBanner = backdropFullPath,
    category = category.name,
)
fun MovieDTO.toMovieDetail() = MovieDetail(
    id = id,
    title = title,
    overview = overview,
    imagePoster = posterFullPath,
    imageBanner = backdropFullPath
)
fun MovieDTO.toEntity(category: MovieCategory) = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    imagePoster = posterFullPath,
    imageBanner = backdropFullPath,
    category = category.name,
)
fun MovieEntity.toMovieList() = MovieList(
    id = id,
    title = title,
    overview = overview,
    imagePoster = imagePoster,
    imageBanner = imageBanner,
    category = category,
)

fun MovieList.toMovieUiData() = MovieUiData(
    id = id,
    title = title,
    overview = overview,
    imagePoster = imagePoster,
    imageBanner = imageBanner,
)
fun MovieDetail.toMovieUiData() = MovieUiData(
    id = id,
    title = title,
    overview = overview,
    imagePoster = imagePoster,
    imageBanner = imageBanner
)




