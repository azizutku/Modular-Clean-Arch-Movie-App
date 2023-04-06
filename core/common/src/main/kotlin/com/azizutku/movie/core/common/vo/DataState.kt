package com.azizutku.movie.core.common.vo

import com.azizutku.movie.core.common.network.NetworkException

sealed class DataState<out T> {

    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: NetworkException) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
    object Idle : DataState<Nothing>()
}
