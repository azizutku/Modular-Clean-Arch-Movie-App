package com.azizutku.movie.feature.watchlist.presentation

import androidx.paging.PagingData
import com.azizutku.movie.core.model.watchlist.WatchlistMovie

sealed class WatchlistUiState {

    data class Success(
        val pagingData: PagingData<WatchlistMovie>,
    ) : WatchlistUiState()
}
