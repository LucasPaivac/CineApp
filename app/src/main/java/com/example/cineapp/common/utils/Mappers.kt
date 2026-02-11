package com.example.cineapp.common.utils

import com.example.cineapp.common.data.local.MovieCategory
import com.example.cineapp.common.data.local.model.MovieEntity
import com.example.cineapp.common.data.remote.model.CastDTO
import com.example.cineapp.common.data.remote.model.DetailMovieDTO
import com.example.cineapp.common.data.remote.model.ListMovieDTO
import com.example.cineapp.screens.detail.model.Cast
import com.example.cineapp.screens.detail.model.CastUiData
import com.example.cineapp.screens.detail.model.DetailMovie
import com.example.cineapp.screens.detail.model.DetailMovieUiData
import com.example.cineapp.screens.list.model.ListMovie
import com.example.cineapp.screens.list.model.ListMovieUiData

//List
fun ListMovieDTO.toMovieList(category: MovieCategory) = ListMovie(
    id = id,
    title = title,
    overview = overview,
    imagePoster = posterFullPath,
    imageBanner = backdropFullPath,
    category = category.name,
)

fun ListMovieDTO.toEntity(category: MovieCategory) = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    imagePoster = posterFullPath,
    imageBanner = backdropFullPath,
    category = category.name,
)
fun MovieEntity.toMovieList() = ListMovie(
    id = id,
    title = title,
    overview = overview,
    imagePoster = imagePoster,
    imageBanner = imageBanner,
    category = category,
)
fun ListMovie.toMovieUiData() = ListMovieUiData(
    id = id,
    title = title,
    overview = overview,
    imagePoster = imagePoster,
    imageBanner = imageBanner,
)

//Detail
fun DetailMovieDTO.toDetailMovie() = DetailMovie(
    id = id,
    title = title,
    overview = overview,
    imagePoster = posterFullPath,
    imageBanner = backdropFullPath,
    genre = genres.firstOrNull()?.name ?: "Geral",
    releaseYear = releaseYear.take(4),
    runtime = formatRuntime(runtime),
    voteAverage = String.format("%.1f", voteAverage),
    cast = cast.castList.take(5).map { it.toCast() }
)

fun CastDTO.toCast() = Cast(
    id = id,
    name = name,
    character = character,
    profileImage = profileFullPath,
)

fun DetailMovie.toDetailMovieUiData() = DetailMovieUiData(
    id = id,
    title = title,
    overview = overview,
    imagePoster = imagePoster,
    imageBanner = imageBanner,
    genre = genre,
    releaseYear = releaseYear.take(4),
    runtime = runtime,
    voteAverage = voteAverage,
    cast = cast.map { it.toCastUiData() },
)

fun Cast.toCastUiData() = CastUiData(
    id = id,
    name = name,
    character = character,
    profileImage = profileImage,
)

private fun formatRuntime(runtime: Int?): String {
    return if (runtime == null || runtime <= 0) "N/A"
    else {
        val hours = runtime / 60
        val minutes = runtime % 60
        "${hours}h ${minutes}min"
    }
}




