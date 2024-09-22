package com.azizutku.movie.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.azizutku.movie.AppState
import com.azizutku.movie.R
import com.azizutku.movie.trending.presentation.navigation.TrendingRoute
import com.azizutku.movie.trending.presentation.navigation.trendingScreen
import com.azizutku.movie.ui.components.MovieAppTopAppBar
import com.azizutku.movie.ui.components.TopAppBarAction
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

@Serializable
data class MovieRoute(val movieId: String)

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

fun NavGraphBuilder.movieScreen(navController: NavHostController) {
    composable<MovieRoute>(
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "movieapp://movie/{movieId}"
            },
        ),
    ) {
        MovieScreen(navController, it.toRoute<MovieRoute>().movieId)
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
                navController.navigateToMovie(movieId = 123)
            },
        ) {
            Text("It is watchlist screen")
        }
    }
}

@Composable
fun MovieScreen(navController: NavHostController, movieId: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        MovieAppTopAppBar(
            title = stringResource(R.string.title_movie_screen),
            actions = listOf(
                TopAppBarAction(
                    imageVector = Icons.Filled.Favorite
                )
            ),
            onNavigationIconClick = navController::navigateUp,
        )
        Text(text = "It is movie screen, movie id: $movieId")
    }
}

fun NavController.navigateToMovie(movieId: Int) {
    val deeplinkUri = "movieapp://movie/{movieId}".replace(
        oldValue = "{movieId}",
        newValue = movieId.toString(),
    ).toUri()
    val request = NavDeepLinkRequest.Builder
        .fromUri(deeplinkUri)
        .build()
    navigate(request)
}
