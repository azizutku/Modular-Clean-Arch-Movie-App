package com.azizutku.movie.core.model

data class UserData(
    val darkThemeConfig: DarkThemeConfig,
)

enum class DarkThemeConfig {
    LIGHT,
    DARK,
}
