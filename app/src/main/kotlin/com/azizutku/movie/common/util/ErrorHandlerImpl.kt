package com.azizutku.movie.common.util

import android.content.Context
import com.azizutku.movie.R
import com.azizutku.movie.common.network.GeneralNetworkExceptionCode
import com.azizutku.movie.common.network.NetworkException
import com.azizutku.movie.common.ui.dialogs.AlertDialog
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ErrorHandlerImpl @Inject constructor(
    @ActivityContext private val context: Context,
    private val alertDialog: AlertDialog,
) : ErrorHandler {

    override var onDefaultPrimaryAction = { /* no-op */ }
    override var defaultTextPrimaryAction = context.getString(R.string.text_button_ok)
    override var handleBeforeGeneralException: (NetworkException) -> Boolean = { false }
    override var handleAfterGeneralException: (NetworkException) -> Boolean = { false }

    override fun handleGeneralException(networkException: NetworkException): Boolean {
        GeneralNetworkExceptionCode.getFromCode(networkException.code) ?: return false
        val exception = setLocalizedMessageIfNeeded(networkException)
        with(alertDialog) {
            setTitle(context.getString(R.string.title_alert_dialog))
            setMessage(exception.message)
            setPositiveButton(defaultTextPrimaryAction) {
                onDefaultPrimaryAction()
                it.dismiss()
            }
            show()
        }
        return true
    }

    @Suppress("ReturnCount")
    override fun handleException(networkException: NetworkException): Boolean {
        if (handleBeforeGeneralException(networkException)) {
            return true
        } else if (handleGeneralException(networkException)) {
            return true
        }
        return handleAfterGeneralException(networkException)
    }

    private fun setLocalizedMessageIfNeeded(exception: NetworkException): NetworkException =
        when (GeneralNetworkExceptionCode.getFromCode(exception.code)) {
            GeneralNetworkExceptionCode.IO_EXCEPTION -> exception.copy(
                message = context.getString(R.string.message_exception_io)
            )
            GeneralNetworkExceptionCode.HTTP_EXCEPTION -> exception.copy(
                message = context.getString(R.string.message_exception_http)
            )
            GeneralNetworkExceptionCode.TIMEOUT_EXCEPTION -> exception.copy(
                message = context.getString(R.string.message_exception_timeout)
            )
            GeneralNetworkExceptionCode.PARSING_EXCEPTION,
            GeneralNetworkExceptionCode.OTHER_EXCEPTION -> exception.copy(
                message = context.getString(R.string.message_exception_unknown)
            )
            null -> exception
        }
}
