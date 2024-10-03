package com.azizutku.movie.feature.movie

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.azizutku.movie.core.model.watchlist.MovieWatchlistState
import com.azizutku.movie.feature.movie.common.domain.model.Movie
import com.azizutku.movie.feature.movie.common.presentation.MovieUiState

class MovieUiStatePreviewParameterProvider : PreviewParameterProvider<MovieUiState> {
    override val values: Sequence<MovieUiState> = sequenceOf(
        MovieUiState.Success(
            movie = Movie(
                id = 1108566,
                description = "An expat PI is hired to investigate a suspicious death in Crete, Greece, where " +
                    "jealousies run deep amongst the victim's powerful family.",
                posterUrl = "https://image.tmdb.org/t/p/w500/WiAEiqelck0NGWplhL5JQR12eg.jpg",
                title = "Killer Heat",
                tagline = "Jealousy can drive anyone to the edge.",
                subtitle = "2024 | Crime | 6.2★"
            ),
            isMovieInWatchlist = MovieWatchlistState(
                isInWatchlist = true,
            )
        ),
        MovieUiState.Success(
            movie = null,
            isMovieInWatchlist = MovieWatchlistState(
                isInWatchlist = false,
            )
        )
    )
}
