package com.azizutku.movie.extensions

import java.util.Locale

internal fun String.capitalize(locale: Locale): String =
    this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
