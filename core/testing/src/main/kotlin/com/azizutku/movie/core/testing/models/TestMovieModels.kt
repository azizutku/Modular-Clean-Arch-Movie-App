package com.azizutku.movie.core.testing.models

import com.azizutku.movie.core.database.model.MovieEntity
import com.azizutku.movie.feature.movie.data.remote.dto.GenreDto
import com.azizutku.movie.feature.movie.data.remote.dto.MovieDto
import com.azizutku.movie.feature.movie.domain.model.Movie

val genreDto = GenreDto(
    id = 1,
    name = "Fantasy",
)

val movie = Movie(
    id = 1,
    description = "Deep inside the mountain of Dovre",
    tagline = "Mountains will move.",
    posterUrl = "https://image.tmdb.org/t/p/w500/9z4jRr43JdtU66P0iy8h18OyLql.jpg",
    title = "Troll",
    subtitle = "2022 | Fantasy | 6.7★",
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
