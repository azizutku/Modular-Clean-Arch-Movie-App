@file:Suppress("MatchingDeclarationName")

package com.azizutku.movie.feature.watchlist.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.azizutku.movie.feature.watchlist.WatchlistScreen
import com.azizutku.movie.ui.handleNavigationAction
import kotlinx.serialization.Serializable

@Serializable
data object WatchlistRoute

fun NavController.navigateToWatchlist(navOptions: NavOptions) = navigate(route = WatchlistRoute, navOptions)

fun NavGraphBuilder.watchlistScreen(navController: NavHostController) {
    composable<WatchlistRoute>(
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "movieapp://watchlist"
            },
        ),
    ) {
        WatchlistScreen(
            onNavigationAction = navController::handleNavigationAction
        )
    }
}
