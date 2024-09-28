@file:Suppress("UsingMaterialAndMaterial3Libraries")

package com.azizutku.movie.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.azizutku.movie.ui.theme.AppTheme
import com.azizutku.movie.ui.theme.PreviewTheme
import com.azizutku.movie.core.ui.common.R as coreUiCommonR

@Composable
fun LoadingDialog() {
    Dialog(
        onDismissRequest = { /* Never will happen, because of the specific properties */ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = CircleShape,
            modifier = Modifier.size(112.dp)
        ) {
            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(coreUiCommonR.raw.lottie_loading),
            )
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )
        }
    }
}

@Composable
@PreviewTheme
private fun LoadingDialogPreview() {
    AppTheme {
        LoadingDialog()
    }
}
