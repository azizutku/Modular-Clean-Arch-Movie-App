package com.azizutku.movie.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.azizutku.movie.core.common.base.BaseViewModel
import com.azizutku.movie.core.common.base.ErrorOwner
import com.azizutku.movie.core.common.base.LoadingOwner
import com.azizutku.movie.core.common.network.GeneralNetworkExceptionCode
import com.azizutku.movie.core.common.network.NetworkException
import com.azizutku.movie.core.ui.common.R as coreUiCommonR

@Composable
fun ErrorLoadingScaffold(
    viewModel: BaseViewModel,
    modifier: Modifier = Modifier,
    handleBeforeGeneralException: (NetworkException) -> Boolean = { false },
    errorPrimaryActionText: String = stringResource(coreUiCommonR.string.text_button_ok),
    onErrorPrimaryAction: () -> Unit = { /* no-op */ },
    loadingDialog: @Composable () -> Unit = { LoadingDialog() },
    errorDialog: @Composable (message: String?) -> Unit = {
        ErrorDialog(message = it, primaryActionText = errorPrimaryActionText, onPrimaryAction = onErrorPrimaryAction)
    },
    content: @Composable () -> Unit,
) {
    val isLoading by (viewModel as? LoadingOwner)?.stateLoading?.collectAsStateWithLifecycle()
        ?: remember { mutableStateOf(false) }
    val errorState by (viewModel as? ErrorOwner)?.stateError?.collectAsStateWithLifecycle(initialValue = null)
        ?: remember { mutableStateOf(null) }
    Box(modifier = modifier) {
        content()
        val error = errorState
        if (error != null) {
            if (handleBeforeGeneralException(error.exception).not()) {
                val message = error.exception.setLocalizedMessageIfNeeded().message
                errorDialog(message)
            }
        } else if (isLoading) {
            loadingDialog()
        }
    }
}

@Composable
private fun NetworkException.setLocalizedMessageIfNeeded(): NetworkException {
    return when (GeneralNetworkExceptionCode.getFromCode(code)) {
        GeneralNetworkExceptionCode.IO_EXCEPTION -> copy(
            message = stringResource(coreUiCommonR.string.message_exception_io)
        )
        GeneralNetworkExceptionCode.HTTP_EXCEPTION -> copy(
            message = stringResource(coreUiCommonR.string.message_exception_http)
        )
        GeneralNetworkExceptionCode.TIMEOUT_EXCEPTION -> copy(
            message = stringResource(coreUiCommonR.string.message_exception_timeout)
        )
        GeneralNetworkExceptionCode.PARSING_EXCEPTION,
        GeneralNetworkExceptionCode.OTHER_EXCEPTION -> copy(
            message = stringResource(coreUiCommonR.string.message_exception_unknown)
        )
        null -> this
    }
}
