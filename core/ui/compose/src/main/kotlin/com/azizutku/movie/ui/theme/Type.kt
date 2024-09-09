package com.azizutku.movie.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.azizutku.movie.core.ui.compose.R

private val regular = Font(R.font.rubik_regular, FontWeight.Normal)
private val medium = Font(R.font.rubik_medium, FontWeight.Medium)
private val bold = Font(R.font.rubik_bold, FontWeight.Bold)

private val fontFamily = FontFamily(regular, medium, bold)
private val baseline = Typography()

val AppTypography = Typography(
    defaultFontFamily = fontFamily,
    h1 = baseline.h1.copy(fontWeight = FontWeight.Bold),
    h2 = baseline.h2.copy(fontWeight = FontWeight.Bold),
    h3 = baseline.h3.copy(fontWeight = FontWeight.Bold),
    h4 = baseline.h4.copy(fontWeight = FontWeight.Bold),
    h5 = baseline.h5.copy(fontWeight = FontWeight.Bold),
    h6 = baseline.h6.copy(fontWeight = FontWeight.Medium),
    subtitle1 = baseline.subtitle1.copy(fontWeight = FontWeight.Medium),
    subtitle2 = baseline.subtitle2.copy(fontWeight = FontWeight.Medium),
    body1 = baseline.body1.copy(fontWeight = FontWeight.Normal),
    body2 = baseline.body2.copy(fontWeight = FontWeight.Normal),
    button = baseline.button.copy(fontWeight = FontWeight.Bold),
    caption = baseline.caption.copy(fontWeight = FontWeight.Normal),
    overline = baseline.overline.copy(fontWeight = FontWeight.Bold),
)
