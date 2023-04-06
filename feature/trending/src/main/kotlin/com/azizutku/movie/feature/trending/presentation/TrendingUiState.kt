package com.azizutku.movie.feature.trending.presentation

import androidx.paging.PagingData
import com.azizutku.movie.feature.trending.domain.model.TrendingMovie

sealed class TrendingUiState {

    data class Success(
        val pagingData: PagingData<TrendingMovie>,
    ) : TrendingUiState()
}
