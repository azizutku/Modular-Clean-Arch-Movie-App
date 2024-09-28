package com.azizutku.movie.feature.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.azizutku.feature.movie.common.presentation.MovieUiState
import com.azizutku.feature.movie.common.presentation.MovieViewModel
import com.azizutku.movie.core.common.extensions.orFalse
import com.azizutku.movie.ui.NavigationAction
import com.azizutku.movie.ui.components.ErrorLoadingScaffold
import com.azizutku.movie.ui.components.MovieAppTopAppBar
import com.azizutku.movie.ui.components.TopAppBarAction
import com.azizutku.feature.movie.common.R as movieCommonR
import com.azizutku.movie.core.ui.common.R as uiCommonR

@Composable
internal fun MovieScreen(
    movieId: Int,
    onNavigationAction: (NavigationAction) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getMovie(movieId)
        viewModel.isInWatchlist(movieId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ErrorLoadingScaffold(
        viewModel = viewModel,
        errorPrimaryActionText = stringResource(uiCommonR.string.text_button_go_back),
        onErrorPrimaryAction = { onNavigationAction(NavigationAction.OnBackButtonClicked) }
    ) {
        MovieScreen(
            modifier = modifier,
            uiState = uiState,
            watchlistAction = { shouldAdd ->
                if (shouldAdd) viewModel.addToWatchlist(movieId) else viewModel.removeFromWatchlist(movieId)
            },
            onNavigationAction = onNavigationAction,
        )
    }
}

@Composable
private fun MovieScreen(
    uiState: MovieUiState,
    modifier: Modifier = Modifier,
    watchlistAction: (shouldAdd: Boolean) -> Unit = {},
    onNavigationAction: (NavigationAction) -> Unit = {},
) {
    if (uiState !is MovieUiState.Success) {
        return
    }
    Column(modifier = modifier) {
        MovieAppTopAppBar(
            title = stringResource(movieCommonR.string.title_fragment_movie),
            actions = listOf(
                TopAppBarAction(
                    imageVector = if (uiState.isMovieInWatchlist?.isInWatchlist.orFalse()) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Filled.FavoriteBorder
                    },
                    contentDescription = stringResource(
                        movieCommonR.string.content_description_app_bar_action_favorites
                    ),
                ) {
                    val shouldAdd = uiState.isMovieInWatchlist?.isInWatchlist.orFalse().not()
                    watchlistAction(shouldAdd)
                }
            ),
            onNavigationIconClick = {
                onNavigationAction(NavigationAction.OnBackButtonClicked)
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text("dsds")
        }
    }
}
