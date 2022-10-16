package com.azizutku.movie.presentation.screens.watchlist

import com.azizutku.movie.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor() : BaseViewModel() {

    override val stateLoading = MutableStateFlow(false)
}
