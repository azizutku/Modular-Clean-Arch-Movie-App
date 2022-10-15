package com.azizutku.movie.util

import android.content.Context
import com.azizutku.movie.R
import com.azizutku.movie.presentation.ui.dialogs.AlertDialog
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ErrorHandlerImpl @Inject constructor(
    @ActivityContext private val context: Context,
    private val alertDialog: AlertDialog,
) : ErrorHandler {

    override var onDefaultPrimaryAction = { /* no-op */ }
    override var defaultTextPrimaryAction = context.getString(R.string.text_button_ok)
    override var handleBeforeGeneralException: (exception: Exception) -> Boolean = { false }
    override var handleAfterGeneralException: (exception: Exception) -> Boolean = { false }

    override fun handleGeneralException(exception: Exception): Boolean {
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
    override fun handleException(exception: Exception): Boolean {
        if (handleBeforeGeneralException(exception)) {
            return true
        } else if (handleGeneralException(exception)) {
            return true
        }
        return handleAfterGeneralException(exception)
    }
}
