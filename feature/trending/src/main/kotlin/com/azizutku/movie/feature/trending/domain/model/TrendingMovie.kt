package com.azizutku.movie.feature.trending.domain.model

data class TrendingMovie(
    val id: Int,
    val description: String,
    val posterUrl: String,
    val releaseDate: String,
    val title: String,
    val rating: String,
)
