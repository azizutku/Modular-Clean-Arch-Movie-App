package com.azizutku.feature.watchlist.common.presentation

import androidx.paging.PagingData
import com.azizutku.movie.core.model.watchlist.WatchlistMovie

sealed class WatchlistUiState {

    data class Success(
        val pagingData: PagingData<WatchlistMovie>,
    ) : WatchlistUiState()
}
