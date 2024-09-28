@file:Suppress("UsingMaterialAndMaterial3Libraries")

package com.azizutku.movie.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.azizutku.movie.ui.theme.AppTheme
import com.azizutku.movie.ui.theme.AppTypography
import com.azizutku.movie.ui.theme.PreviewTheme
import com.azizutku.movie.core.ui.common.R as coreUiCommonR

private const val DialogWidthPercent = 0.8f

@Composable
fun ErrorDialog(
    message: String?,
    primaryActionText: String = stringResource(coreUiCommonR.string.text_button_ok),
    onPrimaryAction: () -> Unit = { /* no-op */ },
) {
    var shouldShowDialog by remember { mutableStateOf(true) }
    if (shouldShowDialog.not()) {
        return
    }
    Dialog(
        onDismissRequest = { /* Never will happen, because of the specific properties */ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(DialogWidthPercent)
        ) {
            ErrorDialogContent(message, onPrimaryAction, primaryActionText) {
                shouldShowDialog = false
            }
        }
    }
}

@Composable
private fun ErrorDialogContent(
    message: String?,
    onPrimaryAction: () -> Unit,
    primaryActionText: String,
    onDismiss: () -> Unit = { /* no-op */ },
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Icon(
            painter = painterResource(id = coreUiCommonR.drawable.ic_line_error_24),
            contentDescription = stringResource(
                coreUiCommonR.string.content_description_alert_dialog_image,
            ),
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colors.onSurface,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(coreUiCommonR.string.title_alert_dialog),
            style = AppTypography.h6,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (message != null) {
            Text(
                text = message,
                style = AppTypography.body1,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
            )
        }
        Spacer(modifier = Modifier.height(36.dp))
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButton(
                onClick = {
                    onPrimaryAction()
                    onDismiss()
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colors.onPrimary,
                ),
            ) {
                Text(text = primaryActionText.uppercase(), style = AppTypography.button)
            }
        }
    }
}

@Composable
@PreviewTheme
private fun LoadingDialogPreview() {
    AppTheme {
        ErrorDialog(message = "Something went wrong")
    }
}
