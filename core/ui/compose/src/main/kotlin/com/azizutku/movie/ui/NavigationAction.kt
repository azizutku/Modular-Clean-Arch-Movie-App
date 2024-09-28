package com.azizutku.movie.ui

import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavHostController

sealed interface NavigationAction {
    data object OnBackButtonClicked : NavigationAction
    data class NavigateTo(val deeplink: String) : NavigationAction
}

fun NavHostController.handleNavigationAction(navigationAction: NavigationAction) {
    when (navigationAction) {
        is NavigationAction.OnBackButtonClicked -> navigateUp()
        is NavigationAction.NavigateTo -> {
            val request = NavDeepLinkRequest.Builder
                .fromUri(navigationAction.deeplink.toUri())
                .build()
            navigate(request)
        }
    }
}
