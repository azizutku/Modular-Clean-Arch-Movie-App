package com.azizutku.movie.feature.movie.presentation

import com.azizutku.movie.core.model.watchlist.MovieWatchlistState
import com.azizutku.movie.feature.movie.domain.model.Movie

sealed interface MovieUiState {

    data class Success(
        val movie: Movie?,
        val isMovieInWatchlist: MovieWatchlistState?,
    ) : MovieUiState

    object Empty : MovieUiState
}
