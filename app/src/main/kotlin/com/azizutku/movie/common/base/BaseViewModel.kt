package com.azizutku.movie.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azizutku.movie.common.vo.DataState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn

const val STOP_TIMEOUT_WHILE_SUBSCRIBED = 5000L

abstract class BaseViewModel : ViewModel() {
    abstract val stateLoading: StateFlow<Boolean>
    open val stateError = MutableSharedFlow<DataState.Error>().asSharedFlow()

    inline fun <reified T> combineForLoading(vararg flows: Flow<T>) = combine(*flows) { args: Array<*> ->
        args.any { it is DataState.Loading }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_WHILE_SUBSCRIBED), false)

    @OptIn(FlowPreview::class)
    fun <T> flattenMergeForError(vararg flows: Flow<T>) = flowOf(*flows)
        .flattenMerge()
        .filterIsInstance<DataState.Error>()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_WHILE_SUBSCRIBED))
}
