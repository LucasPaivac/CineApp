package com.example.cineapp.common.data.remote.model

import com.google.gson.annotations.SerializedName

data class DetailMovieDTO(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val postPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val genres: List<GenreDTO>,
    @SerializedName("release_date")
    val releaseYear: String,
    val runtime: Int,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("credits")
    val cast: CreditsDTO
){
    val posterFullPath: String
        get() = "https://image.tmdb.org/t/p/w300/$postPath"

    val backdropFullPath: String
        get() = "https://image.tmdb.org/t/p/w780/$backdropPath"
}

data class GenreDTO(
    val name: String
)

data class CreditsDTO(
    @SerializedName("cast")
    val castList: List<CastDTO>
)

data class CastDTO(
    val id: Int,
    val name: String,
    @SerializedName("character")
    val character: String,
    @SerializedName("profile_path")
    val profilePath: String?
){
    val profileFullPath: String
        get() = "https://image.tmdb.org/t/p/w300/$profilePath"
}
