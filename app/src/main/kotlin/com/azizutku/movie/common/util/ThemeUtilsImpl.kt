package com.azizutku.movie.common.util

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ThemeUtilsImpl @Inject constructor() : ThemeUtils {
    override fun isDarkTheme(context: Context) = context.resources.configuration.uiMode and
        Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

    override fun isLightTheme(context: Context) = context.resources.configuration.uiMode and
        Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_NO

    override fun toggleTheme(context: Context, delayTimeMillis: Long) {
        setMode(isDarkTheme(context).not(), delayTimeMillis)
    }

    override fun setMode(isNightMode: Boolean, delayTimeMillis: Long) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(delayTimeMillis)
            AppCompatDelegate.setDefaultNightMode(
                if (isNightMode) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )
        }
    }
}
