package com.azizutku.movie.features.trending.domain.model

data class TrendingMovie(
    val id: Int,
    val description: String,
    val posterUrl: String,
    val releaseDate: String,
    val title: String,
    val rating: String,
)
