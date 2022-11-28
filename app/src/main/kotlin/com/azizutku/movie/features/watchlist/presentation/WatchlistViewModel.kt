package com.azizutku.movie.features.watchlist.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.azizutku.movie.common.base.BaseViewModel
import com.azizutku.movie.features.watchlist.domain.usecase.GetMoviesFromWatchlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    val getMoviesInWatchlistUseCase: GetMoviesFromWatchlistUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<WatchlistUiState>(
        WatchlistUiState.Success(PagingData.empty())
    )
    val uiState = _uiState.asStateFlow()

    fun getMoviesFromWatchlist() {
        viewModelScope.launch {
            getMoviesInWatchlistUseCase().cachedIn(viewModelScope).collectLatest { pagingData ->
                _uiState.value = WatchlistUiState.Success(pagingData)
            }
        }
    }
}
