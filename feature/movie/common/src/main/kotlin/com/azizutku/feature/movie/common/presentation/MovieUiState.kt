package com.azizutku.feature.movie.common.presentation

import com.azizutku.feature.movie.common.domain.model.Movie
import com.azizutku.movie.core.model.watchlist.MovieWatchlistState

sealed interface MovieUiState {

    data class Success(
        val movie: Movie?,
        val isMovieInWatchlist: MovieWatchlistState?,
    ) : MovieUiState

    data object Empty : MovieUiState
}
