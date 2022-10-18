package com.azizutku.movie.features.trending.presentation

import androidx.paging.PagingData
import com.azizutku.movie.features.trending.domain.model.TrendingMovie

sealed class TrendingUiState {

    data class Success(
        val pagingData: PagingData<TrendingMovie>,
    ) : TrendingUiState()
}
