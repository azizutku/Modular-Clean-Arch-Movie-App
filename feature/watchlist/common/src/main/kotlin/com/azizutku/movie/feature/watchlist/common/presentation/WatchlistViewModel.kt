package com.azizutku.movie.feature.watchlist.common.presentation

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.azizutku.movie.core.common.base.BaseViewModel
import com.azizutku.movie.core.common.data.repository.UserDataRepository
import com.azizutku.movie.core.common.util.ThemeUtils
import com.azizutku.movie.core.model.watchlist.WatchlistMovie
import com.azizutku.movie.feature.watchlist.common.domain.usecase.GetMoviesFromWatchlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    val getMoviesInWatchlistUseCase: GetMoviesFromWatchlistUseCase,
    private val userDataRepository: UserDataRepository,
    private val themeUtils: ThemeUtils,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<WatchlistUiState>(
        WatchlistUiState.Success(PagingData.empty())
    )
    val uiState = _uiState.asStateFlow()

    // This is a workaround for compose. Observing uiState and processing flow operations on it corrupt UX.
    private val _pagingState = MutableStateFlow<PagingData<WatchlistMovie>>(PagingData.empty())
    val pagingState = _pagingState.asStateFlow()

    fun toggleTheme(context: Context) {
        themeUtils.toggleTheme(context)
        userDataRepository.toggleDarkThemeConfig()
    }

    fun getMoviesFromWatchlist() {
        viewModelScope.launch {
            getMoviesInWatchlistUseCase().cachedIn(viewModelScope).collectLatest { pagingData ->
                _uiState.value = WatchlistUiState.Success(pagingData)
                _pagingState.value = pagingData
            }
        }
    }
}
