package com.azizutku.movie.features.trending.presentation

import com.azizutku.movie.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor() : BaseViewModel() {

    override val stateLoading = MutableStateFlow(false)
}
