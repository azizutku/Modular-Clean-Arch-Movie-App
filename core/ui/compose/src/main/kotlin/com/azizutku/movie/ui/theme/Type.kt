package com.azizutku.movie.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.azizutku.movie.core.ui.common.R as uiCommonR

private val regular = Font(uiCommonR.font.rubik_regular, FontWeight.Normal)
private val medium = Font(uiCommonR.font.rubik_medium, FontWeight.Medium)
private val bold = Font(uiCommonR.font.rubik_bold, FontWeight.Bold)

private val fontFamily = FontFamily(regular, medium, bold)
private val baseline = Typography()

val AppTypography = Typography(
    defaultFontFamily = fontFamily,
    h1 = baseline.h1.copy(fontWeight = FontWeight.Bold, fontFamily = fontFamily),
    h2 = baseline.h2.copy(fontWeight = FontWeight.Bold, fontFamily = fontFamily),
    h3 = baseline.h3.copy(fontWeight = FontWeight.Bold, fontFamily = fontFamily),
    h4 = baseline.h4.copy(fontWeight = FontWeight.Bold, fontFamily = fontFamily),
    h5 = baseline.h5.copy(fontWeight = FontWeight.Bold, fontFamily = fontFamily),
    h6 = baseline.h6.copy(fontWeight = FontWeight.Medium, fontFamily = fontFamily),
    subtitle1 = baseline.subtitle1.copy(fontWeight = FontWeight.Medium, fontFamily = fontFamily),
    subtitle2 = baseline.subtitle2.copy(fontWeight = FontWeight.Medium, fontFamily = fontFamily),
    body1 = baseline.body1.copy(fontWeight = FontWeight.Normal, fontFamily = fontFamily),
    body2 = baseline.body2.copy(fontWeight = FontWeight.Normal, fontFamily = fontFamily),
    button = baseline.button.copy(fontWeight = FontWeight.Bold, fontFamily = fontFamily),
    caption = baseline.caption.copy(fontWeight = FontWeight.Normal, fontFamily = fontFamily),
    overline = baseline.overline.copy(fontWeight = FontWeight.Bold, fontFamily = fontFamily),
)
