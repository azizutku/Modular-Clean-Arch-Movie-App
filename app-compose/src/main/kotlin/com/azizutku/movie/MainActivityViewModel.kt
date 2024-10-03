package com.azizutku.movie

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azizutku.movie.core.common.data.repository.UserDataRepository
import com.azizutku.movie.core.common.util.ThemeUtils
import com.azizutku.movie.core.model.DarkThemeConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val themeUtils: ThemeUtils
) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = userDataRepository.userData.map {
        MainActivityUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )

    fun setInitialDarkThemeConfig(context: Context) {
        val isNightMode = themeUtils.isDarkTheme(context)
        val themeConfig = if (isNightMode) DarkThemeConfig.DARK else DarkThemeConfig.LIGHT
        userDataRepository.setDarkThemeConfig(themeConfig)
    }
}
