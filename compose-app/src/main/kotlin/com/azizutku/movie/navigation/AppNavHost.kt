package com.azizutku.movie.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.azizutku.movie.AppState
import com.azizutku.movie.R
import com.azizutku.movie.feature.movie.navigation.movieScreen
import com.azizutku.movie.trending.presentation.navigation.TrendingRoute
import com.azizutku.movie.trending.presentation.navigation.trendingScreen
import com.azizutku.movie.ui.components.MovieAppTopAppBar
import kotlinx.serialization.Serializable

@Composable
fun AppNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = TrendingRoute,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        },
        modifier = modifier,
    ) {
        trendingScreen(navController)
        watchlistScreen(navController)
        movieScreen(navController)
    }
}

@Serializable
data object WatchlistRoute

fun NavController.navigateToWatchlist(navOptions: NavOptions) =
    navigate(route = WatchlistRoute, navOptions)

fun NavGraphBuilder.watchlistScreen(navController: NavHostController) {
    composable<WatchlistRoute>(
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "movieapp://watchlist"
            },
        ),
    ) {
        WatchlistScreen(navController)
    }
}

@Composable
fun WatchlistScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        MovieAppTopAppBar(
            title = stringResource(R.string.title_watchlist_screen),
            hideNavigationIcon = true,
            onNavigationIconClick = navController::navigateUp,
        )
        Button(
            onClick = {
                // no-op
            },
        ) {
            Text("It is watchlist screen")
        }
    }
}
