package com.azizutku.movie.features.trending.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.azizutku.movie.common.base.BaseViewModel
import com.azizutku.movie.features.trending.domain.usecase.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<TrendingUiState>(
        TrendingUiState.Success(PagingData.empty())
    )
    val uiState = _uiState.asStateFlow()

    init {
        getTrendingMovies()
    }

    private fun getTrendingMovies() {
        viewModelScope.launch {
            getTrendingMoviesUseCase().cachedIn(viewModelScope).collectLatest { pagingData ->
                _uiState.value = TrendingUiState.Success(pagingData)
            }
        }
    }
}
