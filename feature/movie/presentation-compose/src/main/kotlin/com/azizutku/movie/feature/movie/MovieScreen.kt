package com.azizutku.movie.feature.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.azizutku.feature.movie.common.presentation.MovieViewModel
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
    // val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ErrorLoadingScaffold(
        viewModel = viewModel,
        errorPrimaryActionText = stringResource(uiCommonR.string.text_button_go_back),
        onErrorPrimaryAction = { onNavigationAction(NavigationAction.OnBackButtonClicked) }
    ) {
        MovieScreen(
            modifier = modifier,
            onNavigationAction = onNavigationAction,
        )
    }
}

@Composable
private fun MovieScreen(
    modifier: Modifier = Modifier,
    onNavigationAction: (NavigationAction) -> Unit = {},
) {
    Column(modifier = modifier) {
        MovieAppTopAppBar(
            title = stringResource(movieCommonR.string.title_fragment_movie),
            hideNavigationIcon = true,
            actions = listOf(
                TopAppBarAction(
                    imageVector = ImageVector.vectorResource(movieCommonR.drawable.ic_line_favorite_24),
                    contentDescription = stringResource(
                        movieCommonR.string.content_description_app_bar_action_favorites
                    ),
                ) {
                    // Handle favorites action
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
