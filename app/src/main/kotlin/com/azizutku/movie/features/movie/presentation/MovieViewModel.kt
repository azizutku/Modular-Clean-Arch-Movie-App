package com.azizutku.movie.features.movie.presentation

import androidx.lifecycle.viewModelScope
import com.azizutku.movie.common.base.BaseViewModel
import com.azizutku.movie.common.base.ErrorOwner
import com.azizutku.movie.common.base.LoadingOwner
import com.azizutku.movie.common.base.STOP_TIMEOUT_WHILE_SUBSCRIBED
import com.azizutku.movie.common.base.combineForLoading
import com.azizutku.movie.common.base.flattenMergeForError
import com.azizutku.movie.common.vo.DataState
import com.azizutku.movie.features.movie.domain.model.Movie
import com.azizutku.movie.features.movie.domain.usecase.GetMovieUseCase
import com.azizutku.movie.features.watchlist.domain.model.MovieWatchlistState
import com.azizutku.movie.features.watchlist.domain.usecase.AddMovieToWatchlistUseCase
import com.azizutku.movie.features.watchlist.domain.usecase.CheckMovieInWatchlistUseCase
import com.azizutku.movie.features.watchlist.domain.usecase.RemoveMovieFromWatchlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val addMovieToWatchlistUseCase: AddMovieToWatchlistUseCase,
    private val removeMovieFromWatchlistUseCase: RemoveMovieFromWatchlistUseCase,
    private val checkMovieInWatchlistUseCase: CheckMovieInWatchlistUseCase,
) : BaseViewModel(), LoadingOwner, ErrorOwner {

    private val _stateMovies = MutableStateFlow<DataState<Movie>>(DataState.Idle)

    private val _stateIsInWatchlist = MutableStateFlow<DataState<MovieWatchlistState>>(DataState.Idle)

    val uiState = getMovieUiStateStream(_stateMovies, _stateIsInWatchlist).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_WHILE_SUBSCRIBED),
        initialValue = MovieUiState.Empty,
    )

    override val stateLoading = combineForLoading(_stateMovies, _stateIsInWatchlist)

    override val stateError = flattenMergeForError(_stateMovies)

    fun getMovie(movieId: Int) {
        viewModelScope.launch {
            getMovieUseCase(movieId).collectLatest {
                _stateMovies.value = it
            }
        }
    }

    fun addToWatchlist(movieId: Int) {
        viewModelScope.launch {
            addMovieToWatchlistUseCase(movieId).collectLatest {
                _stateIsInWatchlist.value = it
            }
        }
    }

    fun removeFromWatchlist(movieId: Int) {
        viewModelScope.launch {
            removeMovieFromWatchlistUseCase(movieId).collectLatest {
                _stateIsInWatchlist.value = it
            }
        }
    }

    fun isInWatchlist(movieId: Int) {
        viewModelScope.launch {
            checkMovieInWatchlistUseCase(movieId).collectLatest {
                _stateIsInWatchlist.value = it
            }
        }
    }
}

private fun getMovieUiStateStream(
    stateMovies: Flow<DataState<Movie>>,
    stateIsInWatchlist: Flow<DataState<MovieWatchlistState>>,
): Flow<MovieUiState> = combine(stateMovies, stateIsInWatchlist) { movieState, isInWatchlistState ->
    if (movieState !is DataState.Success && isInWatchlistState !is DataState.Success) {
        return@combine MovieUiState.Empty
    }

    val movie = (movieState as? DataState.Success)?.data
    val isInFavorites = (isInWatchlistState as? DataState.Success)?.data ?: MovieWatchlistState(false)

    return@combine MovieUiState.Success(
        movie = movie,
        isMovieInWatchlist = isInFavorites,
    )
}
