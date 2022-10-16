package com.azizutku.movie.common.util

import android.content.Context

private const val DELAY_TO_APPLY_THEME = 500L

interface ThemeUtils {
    fun isDarkTheme(context: Context): Boolean

    fun isLightTheme(context: Context): Boolean

    fun toggleTheme(context: Context, delayTimeMillis: Long = DELAY_TO_APPLY_THEME)

    fun setMode(isNightMode: Boolean, delayTimeMillis: Long = DELAY_TO_APPLY_THEME)
}
