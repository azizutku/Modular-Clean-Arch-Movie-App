package com.azizutku.movie.core.common.extensions

import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import com.azizutku.movie.core.common.R

fun NavController.navigateToMovie(deeplinkPattern: String, argumentPattern: String, movieId: Int) {
    val deeplinkUri = deeplinkPattern.replace(
        oldValue = "{$argumentPattern}",
        newValue = movieId.toString(),
    ).toUri()
    val request = NavDeepLinkRequest.Builder
        .fromUri(deeplinkUri)
        .build()
    navigate(request, getNavOptionsWithAnimation())
}

private fun getNavOptionsWithAnimation(): NavOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .build()
