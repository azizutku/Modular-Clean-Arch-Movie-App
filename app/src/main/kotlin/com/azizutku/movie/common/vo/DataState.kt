package com.azizutku.movie.common.vo

import com.azizutku.movie.common.network.NetworkException

sealed class DataState<out T> {

    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: NetworkException) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
    object Idle : DataState<Nothing>()
}
