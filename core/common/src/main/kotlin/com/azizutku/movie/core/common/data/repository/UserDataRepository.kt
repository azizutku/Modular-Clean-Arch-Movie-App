package com.azizutku.movie.core.common.data.repository

import com.azizutku.movie.core.model.DarkThemeConfig
import com.azizutku.movie.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>

    fun toggleDarkThemeConfig()
    fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)
}
