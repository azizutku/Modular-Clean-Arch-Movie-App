// TODO: Move this file shared folder between androidTest and test when testFixtures supports Kotlin.

package com.azizutku.movie.models

import com.azizutku.movie.core.database.model.WatchlistEntity

val watchlistEntity1 = WatchlistEntity(
    movieId = 1,
    addedAt = 1670866175000,
)

val watchlistEntity2 = WatchlistEntity(
    movieId = 2,
    addedAt = 1670866176000,
)
