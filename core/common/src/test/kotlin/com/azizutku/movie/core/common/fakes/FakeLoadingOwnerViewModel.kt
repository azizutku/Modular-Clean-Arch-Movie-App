package com.azizutku.movie.core.common.fakes

import androidx.lifecycle.ViewModel
import com.azizutku.movie.core.common.base.LoadingOwner
import com.azizutku.movie.core.common.base.combineForLoading
import com.azizutku.movie.core.common.vo.DataState
import kotlinx.coroutines.flow.MutableStateFlow

class FakeLoadingOwnerViewModel : ViewModel(), LoadingOwner {
    val stateFirst = MutableStateFlow<DataState<Int>>(DataState.Idle)
    val stateSecond = MutableStateFlow<DataState<Int>>(DataState.Idle)

    override val stateLoading = combineForLoading(stateFirst, stateSecond)
}
