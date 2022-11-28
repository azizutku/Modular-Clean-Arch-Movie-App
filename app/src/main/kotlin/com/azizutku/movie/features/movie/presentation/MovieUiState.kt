package com.azizutku.movie.features.movie.presentation

import com.azizutku.movie.features.movie.domain.model.Movie
import com.azizutku.movie.features.watchlist.domain.model.MovieWatchlistState

sealed interface MovieUiState {

    data class Success(
        val movie: Movie?,
        val isMovieInWatchlist: MovieWatchlistState?,
    ) : MovieUiState

    object Empty : MovieUiState
}
