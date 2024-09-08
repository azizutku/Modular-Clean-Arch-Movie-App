package com.azizutku.movie.feature.trending.common.presentation

import androidx.paging.PagingData
import com.azizutku.movie.feature.trending.common.domain.model.TrendingMovie

sealed class TrendingUiState {

    data class Success(
        val pagingData: PagingData<TrendingMovie>,
    ) : TrendingUiState()
}
