package com.azizutku.movie.features.watchlist.presentation

import com.azizutku.movie.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor() : BaseViewModel() {

    override val stateLoading = MutableStateFlow(false)
}
