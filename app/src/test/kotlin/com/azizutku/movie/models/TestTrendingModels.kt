// TODO: Move this file shared folder between androidTest and test when testFixtures supports Kotlin.

package com.azizutku.movie.models

import com.azizutku.movie.features.trending.data.local.entity.TrendingMovieEntity
import com.azizutku.movie.features.trending.data.remote.dto.TrendingDto
import com.azizutku.movie.features.trending.data.remote.dto.TrendingMovieDto

val trendingMovieDto = TrendingMovieDto(
    id = 1,
    description = "Deep inside the mountain of Dovre",
    posterPath = "/9z4jRr43JdtU66P0iy8h18OyLql.jpg",
    releaseDate = "2022-12-01",
    title = "Troll",
    voteAverage = 6.78,
)

val trendingMovieDto2 = TrendingMovieDto(
    id = 2,
    description = "Deep inside the mountain of Dovre",
    posterPath = "/9z4jRr43JdtU66P0iy8h18OyLql.jpg",
    releaseDate = "2022-12-01",
    title = "Troll",
    voteAverage = 6.78,
)

val trendingMovieDto3 = TrendingMovieDto(
    id = 3,
    description = "Deep inside the mountain of Dovre",
    posterPath = "/9z4jRr43JdtU66P0iy8h18OyLql.jpg",
    releaseDate = "2022-12-01",
    title = "Troll",
    voteAverage = 6.78,
)

val testTrendingDto = TrendingDto(
    page = 1,
    movies = listOf(trendingMovieDto, trendingMovieDto2, trendingMovieDto2),
    totalPages = 1000,
    totalResults = 20000,
)

val trendingMovieEntity = TrendingMovieEntity(
    id = 1,
    description = "Deep inside the mountain of Dovre",
    posterPath = "/9z4jRr43JdtU66P0iy8h18OyLql.jpg",
    releaseDate = "2022-12-01",
    title = "Troll",
    voteAverage = 6.78,
)

val trendingMovieEntity2 = TrendingMovieEntity(
    id = 2,
    description = "Deep inside the mountain of Dovre",
    posterPath = "/9z4jRr43JdtU66P0iy8h18OyLql.jpg",
    releaseDate = "2022-12-01",
    title = "Troll",
    voteAverage = 6.78,
)

val trendingMovieEntity3 = TrendingMovieEntity(
    id = 3,
    description = "Deep inside the mountain of Dovre",
    posterPath = "/9z4jRr43JdtU66P0iy8h18OyLql.jpg",
    releaseDate = "2022-12-01",
    title = "Troll",
    voteAverage = 6.78,
)

val trendingMovieEntity4 = TrendingMovieEntity(
    id = 4,
    description = "Deep inside the mountain of Dovre",
    posterPath = "/9z4jRr43JdtU66P0iy8h18OyLql.jpg",
    releaseDate = "2022-12-01",
    title = "Troll",
    voteAverage = 6.78,
)
