package com.azizutku.movie.feature.trending.common.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.azizutku.movie.core.common.base.BaseViewModel
import com.azizutku.movie.feature.trending.common.domain.model.TrendingMovie
import com.azizutku.movie.feature.trending.common.domain.usecase.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<TrendingUiState>(
        TrendingUiState.Success(PagingData.empty())
    )
    val uiState = _uiState.asStateFlow()

    // This is a workaround for compose. It is not updating paging data.
    private val _pagingState = MutableStateFlow<PagingData<TrendingMovie>>(PagingData.empty())
    val pagingState = _pagingState.asStateFlow()

    init {
        getTrendingMovies()
    }

    private fun getTrendingMovies() {
        viewModelScope.launch {
            getTrendingMoviesUseCase().cachedIn(viewModelScope).collectLatest { pagingData ->
                _uiState.value = TrendingUiState.Success(pagingData)
                _pagingState.value = pagingData
            }
        }
    }
}
