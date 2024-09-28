package com.azizutku.movie.core.common.data.repository

import com.azizutku.movie.core.model.DarkThemeConfig
import com.azizutku.movie.core.model.UserData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserDataRepositoryImpl @Inject constructor() : UserDataRepository {
    private val _userData = MutableStateFlow(
        UserData(darkThemeConfig = DarkThemeConfig.LIGHT),
    )

    override val userData: Flow<UserData> = _userData.asStateFlow()

    override fun toggleDarkThemeConfig() {
        _userData.update {
            val targetConfig = when (it.darkThemeConfig) {
                DarkThemeConfig.LIGHT -> DarkThemeConfig.DARK
                DarkThemeConfig.DARK -> DarkThemeConfig.LIGHT
            }
            it.copy(darkThemeConfig = targetConfig)
        }
    }

    override fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        _userData.update {
            it.copy(darkThemeConfig = darkThemeConfig)
        }
    }
}
