package com.azizutku.movie.core.common.fakes

import androidx.lifecycle.ViewModel
import com.azizutku.movie.core.common.base.ErrorOwner
import com.azizutku.movie.core.common.base.flattenMergeForError
import com.azizutku.movie.core.common.vo.DataState
import kotlinx.coroutines.flow.MutableStateFlow

class FakeErrorOwnerViewModel : ViewModel(), ErrorOwner {
    val stateFirst = MutableStateFlow<DataState<Int>>(DataState.Idle)
    val stateSecond = MutableStateFlow<DataState<Int>>(DataState.Idle)

    override val stateError = flattenMergeForError(stateFirst, stateSecond)
}
