package com.azizutku.movie.features.movie.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    @SerialName("id")
    val id: Int,
    @SerialName("overview")
    val description: String?,
    @SerialName("tagline")
    val tagline: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("genres")
    val genres: List<GenreDto?>?,
)
