package com.azizutku.movie.util

import com.azizutku.movie.common.network.NetworkException

interface ErrorHandler {

    var onDefaultPrimaryAction: () -> Unit
    var defaultTextPrimaryAction: String
    var handleBeforeGeneralException: (networkException: NetworkException) -> Boolean
    var handleAfterGeneralException: (networkException: NetworkException) -> Boolean

    fun handleException(networkException: NetworkException): Boolean
    fun handleGeneralException(networkException: NetworkException): Boolean
}
