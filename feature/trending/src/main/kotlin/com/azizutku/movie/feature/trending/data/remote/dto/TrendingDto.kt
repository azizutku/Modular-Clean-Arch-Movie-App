package com.azizutku.movie.feature.trending.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingDto(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val movies: List<TrendingMovieDto> = emptyList(),
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)
