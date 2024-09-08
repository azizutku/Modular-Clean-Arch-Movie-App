@file:Suppress("MatchingDeclarationName")

package com.azizutku.movie.trending.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.azizutku.movie.trending.presentation.TrendingScreen
import kotlinx.serialization.Serializable

@Serializable
data object TrendingRoute

fun NavController.navigateToTrending(navOptions: NavOptions) = navigate(route = TrendingRoute, navOptions)

fun NavGraphBuilder.trendingScreen() {
    composable<TrendingRoute>(
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "movieapp://trending"
            },
        ),
    ) {
        TrendingScreen()
    }
}
