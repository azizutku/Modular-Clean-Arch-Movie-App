package com.azizutku.movie.ui

import androidx.navigation.NavHostController

sealed interface NavigationAction {
    data object OnBackButtonClicked : NavigationAction
    data class NavigateTo(val deeplink: String) : NavigationAction
}

fun NavHostController.handleNavigationAction(navigationAction: NavigationAction) {
    when (navigationAction) {
        is NavigationAction.OnBackButtonClicked -> navigateUp()
        is NavigationAction.NavigateTo -> navigate(navigationAction.deeplink)
    }
}
