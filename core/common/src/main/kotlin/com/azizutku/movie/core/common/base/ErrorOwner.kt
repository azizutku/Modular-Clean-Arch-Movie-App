package com.azizutku.movie.core.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azizutku.movie.core.common.vo.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn

interface ErrorOwner {
    val stateError: SharedFlow<DataState.Error>
}

context(ViewModel)
fun <T> ErrorOwner.flattenMergeForError(vararg flows: Flow<T>) = flowOf(*flows)
    .flattenMerge()
    .filterIsInstance<DataState.Error>()
    .shareIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_WHILE_SUBSCRIBED))
