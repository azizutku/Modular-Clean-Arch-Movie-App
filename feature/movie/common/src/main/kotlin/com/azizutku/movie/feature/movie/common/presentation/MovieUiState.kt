package com.azizutku.movie.feature.movie.common.presentation

import com.azizutku.movie.core.model.watchlist.MovieWatchlistState
import com.azizutku.movie.feature.movie.common.domain.model.Movie

sealed interface MovieUiState {

    data class Success(
        val movie: Movie?,
        val isMovieInWatchlist: MovieWatchlistState?,
    ) : MovieUiState

    data object Empty : MovieUiState
}
