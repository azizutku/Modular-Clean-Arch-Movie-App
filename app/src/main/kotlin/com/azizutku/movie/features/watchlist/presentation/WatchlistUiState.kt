package com.azizutku.movie.features.watchlist.presentation

import androidx.paging.PagingData
import com.azizutku.movie.features.watchlist.domain.model.WatchlistMovie

sealed class WatchlistUiState {

    data class Success(
        val pagingData: PagingData<WatchlistMovie>,
    ) : WatchlistUiState()
}
