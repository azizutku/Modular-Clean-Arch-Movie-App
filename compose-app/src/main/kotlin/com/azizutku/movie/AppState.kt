package com.azizutku.movie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.azizutku.movie.navigation.TopLevelDestination
import com.azizutku.movie.navigation.navigateToWatchlist
import com.azizutku.movie.trending.presentation.navigation.navigateToTrending

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
): AppState {
    return remember(navController) {
        AppState(navController = navController)
    }
}

@Stable
class AppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            val destination = currentDestination
            destination ?: return lastKnownTopLevelDestination
            return topLevelDestinations.find { topLevelDestination ->
                destination.hasRoute(route = topLevelDestination.route)
            }?.also {
                lastKnownTopLevelDestination = it
            }
        }

    private val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    private var lastKnownTopLevelDestination: TopLevelDestination? = null

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.TRENDING -> navController.navigateToTrending(topLevelNavOptions)
            TopLevelDestination.WATCHLIST -> navController.navigateToWatchlist(topLevelNavOptions)
        }
    }
}
