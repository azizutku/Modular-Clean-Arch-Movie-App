package com.azizutku.movie.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LocalColors = staticCompositionLocalOf { lightColorPalette }

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorPalette = if (darkTheme) darkColorPalette else lightColorPalette
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            window?.statusBarColor = colorPalette.background.toArgb()
            window?.let {
                WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = darkTheme.not()
            }
        }
    }
    CompositionLocalProvider(LocalColors provides colorPalette) {
        MaterialTheme(
            colors = colorPalette.material,
            typography = AppTypography,
            content = {
                CompositionLocalProvider(
                    LocalContentAlpha provides 1f
                ) {
                    content()
                }
            },
        )
    }
}

val MaterialTheme.appColors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current
