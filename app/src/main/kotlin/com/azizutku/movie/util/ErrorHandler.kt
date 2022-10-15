package com.azizutku.movie.util


interface ErrorHandler {

    var onDefaultPrimaryAction: () -> Unit
    var defaultTextPrimaryAction: String
    var handleBeforeGeneralException: (exception: Exception) -> Boolean
    var handleAfterGeneralException: (exception: Exception) -> Boolean

    fun handleException(exception: Exception): Boolean
    fun handleGeneralException(exception: Exception): Boolean
}
