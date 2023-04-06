@file:Suppress("UnusedImports")
package com.azizutku.movie.core.common.extensions

import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.azizutku.movie.core.common.R

context(Fragment)
fun NavController.navigateToMovie(movieId: Int) {
    val deeplinkUri = getString(R.string.deep_link_movie).replace(
        oldValue = "{${getString(R.string.argument_movie_id)}}",
        newValue = movieId.toString(),
    ).toUri()
    val request = NavDeepLinkRequest.Builder
        .fromUri(deeplinkUri)
        .build()
    findNavController().navigate(request, getNavOptionsWithAnimation())
}

private fun getNavOptionsWithAnimation(): NavOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .build()
