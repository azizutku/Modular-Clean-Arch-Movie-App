// TODO: Move this file shared folder between androidTest and test when testFixtures supports Kotlin.

package com.azizutku.movie.models

import com.azizutku.movie.features.movie.data.local.entity.MovieEntity
import com.azizutku.movie.features.movie.data.remote.dto.GenreDto
import com.azizutku.movie.features.movie.data.remote.dto.MovieDto

val genreDto = GenreDto(
    id = 1,
    name = "Fantasy",
)

val movieDto = MovieDto(
    id = 1,
    description = "Deep inside the mountain of Dovre",
    tagline = "Mountains will move.",
    posterPath = "/9z4jRr43JdtU66P0iy8h18OyLql.jpg",
    releaseDate = "2022-12-01",
    title = "Troll",
    voteAverage = 6.78,
    genres = listOf(genreDto),
)

val movieEntity = MovieEntity(
    id = 1,
    description = "Deep inside the mountain of Dovre",
    tagline = "Mountains will move.",
    posterPath = "/9z4jRr43JdtU66P0iy8h18OyLql.jpg",
    releaseDate = "2022-12-01",
    title = "Troll",
    voteAverage = 6.78,
    genre = "Fantasy",
)

val movieEntity2 = MovieEntity(
    id = 2,
    description = "In the 1930s, three friends—a doctor",
    tagline = "Let the love, murder and conspiracy begin.",
    posterPath = "/6sJcVzGCwrDCBMV0DU6eRzA2UxM.jpg",
    releaseDate = "2022-09-27",
    title = "Amsterdam",
    voteAverage = 6.10,
    genre = "Crime",
)
