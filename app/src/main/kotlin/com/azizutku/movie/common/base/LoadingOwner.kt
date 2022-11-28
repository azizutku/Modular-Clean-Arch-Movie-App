package com.azizutku.movie.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azizutku.movie.common.vo.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

interface LoadingOwner {
    val stateLoading: StateFlow<Boolean>
}

context(ViewModel)
inline fun <reified T> LoadingOwner.combineForLoading(vararg flows: Flow<T>) = combine(*flows) { args: Array<*> ->
    args.any { it is DataState.Loading }
}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_WHILE_SUBSCRIBED), false)
