package com.azizutku.movie.features.trending.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingMovieDto(
    @SerialName("id")
    val id: Int,
    @SerialName("overview")
    val description: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("vote_average")
    val voteAverage: Double?,
)
