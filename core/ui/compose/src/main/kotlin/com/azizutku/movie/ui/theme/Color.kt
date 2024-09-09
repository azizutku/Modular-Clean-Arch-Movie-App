package com.azizutku.movie.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

// Light color set
private val primaryLight = Color(0xFFFAFAFA)
private val primaryVariantLight = Color(0xFFFFFFFF)
private val secondaryLight = Color(0xFF5C5E7C)
private val secondaryVariantLight = Color(0xFF3A3C5A)
private val surfaceLight = Color(0xFFFFFFFF)
private val backgroundLight = Color(0xFFFAFAFA)
private val onBackgroundLight = Color(0xFF000000)
private val onSurfaceLight = Color(0xFF000000)
private val onPrimaryLight = Color(0xFF000000)
private val onSecondaryLight = Color(0xFF000000)
private val errorLight = Color(0xFFB00020)
private val onErrorLight = Color(0xFFFFFFFF)

private val bottomNavigationItemActiveLight = Color(0xFF1B1B1B)
private val bottomNavigationItemInactiveLight = Color(0xFF9D9D9D)

// Dark color set
private val primaryDark = Color(0xFF191932)
private val primaryVariantDark = Color(0xFF262648)
private val secondaryDark = Color(0xFFEC2B44)
private val secondaryVariantDark = Color(0xFFF14158)
private val surfaceDark = Color(0xFF191932)
private val backgroundDark = Color(0xFF191932)
private val onBackgroundDark = Color(0xFF191932)
private val onSurfaceDark = Color(0xFFFFFFFF)
private val onPrimaryDark = Color(0xFFFFFFFF)
private val onSecondaryDark = Color(0xFFFFFFFF)
private val errorDark = Color(0xFFCF6679)
private val onErrorDark = Color(0xFF000000)

private val bottomNavigationItemActiveDark = Color(0xFFFFFFFF)
private val bottomNavigationItemInactiveDark = Color(0xFFDFDFDF)

data class AppColors(
    val material: Colors,
    val bottomNavigationItemActive: Color,
    val bottomNavigationItemInactive: Color,
) {
    val primary: Color get() = material.primary
    val primaryVariant: Color get() = material.primaryVariant
    val secondary: Color get() = material.secondary
    val secondaryVariant: Color get() = material.secondaryVariant
    val background: Color get() = material.background
    val surface: Color get() = material.surface
    val error: Color get() = material.error
    val onPrimary: Color get() = material.onPrimary
    val onSecondary: Color get() = material.onSecondary
    val onBackground: Color get() = material.onBackground
    val onSurface: Color get() = material.onSurface
    val onError: Color get() = material.onError
    val isLight: Boolean get() = material.isLight
}

val lightColorPalette = AppColors(
    material = Colors(
        primary = primaryLight,
        primaryVariant = primaryVariantLight,
        secondary = secondaryLight,
        secondaryVariant = secondaryVariantLight,
        background = backgroundLight,
        surface = surfaceLight,
        onBackground = onBackgroundLight,
        onSurface = onSurfaceLight,
        error = errorLight,
        onPrimary = onPrimaryLight,
        onSecondary = onSecondaryLight,
        onError = onErrorLight,
        isLight = true,
    ),
    bottomNavigationItemActive = bottomNavigationItemActiveLight,
    bottomNavigationItemInactive = bottomNavigationItemInactiveLight,
)

val darkColorPalette = AppColors(
    material = Colors(
        primary = primaryDark,
        primaryVariant = primaryVariantDark,
        secondary = secondaryDark,
        secondaryVariant = secondaryVariantDark,
        background = backgroundDark,
        surface = surfaceDark,
        onBackground = onBackgroundDark,
        onSurface = onSurfaceDark,
        error = errorDark,
        onPrimary = onPrimaryDark,
        onSecondary = onSecondaryDark,
        onError = onErrorDark,
        isLight = false,
    ),
    bottomNavigationItemActive = bottomNavigationItemActiveDark,
    bottomNavigationItemInactive = bottomNavigationItemInactiveDark,
)
