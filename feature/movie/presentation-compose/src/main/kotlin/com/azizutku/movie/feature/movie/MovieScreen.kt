package com.azizutku.movie.feature.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.azizutku.feature.movie.common.domain.model.Movie
import com.azizutku.feature.movie.common.presentation.MovieUiState
import com.azizutku.feature.movie.common.presentation.MovieViewModel
import com.azizutku.movie.core.common.extensions.orFalse
import com.azizutku.movie.ui.NavigationAction
import com.azizutku.movie.ui.components.ErrorLoadingScaffold
import com.azizutku.movie.ui.components.MovieAppTopAppBar
import com.azizutku.movie.ui.components.TopAppBarAction
import com.azizutku.movie.ui.theme.AppTheme
import com.azizutku.movie.ui.theme.AppTypography
import com.azizutku.movie.ui.theme.PreviewTheme
import com.azizutku.feature.movie.common.R as movieCommonR
import com.azizutku.movie.core.ui.common.R as uiCommonR

private const val ImageWidthPercent = 0.60f
private const val LogoWidthPercent = 0.50f
private const val DividerWidthPercent = 0.70f
private const val ImageAspectRatio = 1f / 1.5f

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
        onErrorPrimaryAction = { onNavigationAction(NavigationAction.OnBackButtonClicked) },
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
    val movie = uiState.movie
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
                        movieCommonR.string.content_description_app_bar_action_favorites,
                    ),
                ) {
                    val shouldAdd = uiState.isMovieInWatchlist?.isInWatchlist.orFalse().not()
                    watchlistAction(shouldAdd)
                },
            ),
            onNavigationIconClick = {
                onNavigationAction(NavigationAction.OnBackButtonClicked)
            },
        )
        if (movie != null) {
            MovieContent(movie = movie)
        }
    }
}

@Suppress("LongMethod")
@Composable
private fun MovieContent(
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = stringResource(uiCommonR.string.content_description_alert_dialog_image),
            placeholder = painterResource(id = uiCommonR.drawable.bg_placeholder_movie_image),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(ImageWidthPercent)
                .aspectRatio(ImageAspectRatio)
                .clip(RoundedCornerShape(32.dp))
                .shadow(4.dp),
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = movie.title,
            style = AppTypography.h5,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Text(
            text = movie.subtitle,
            style = AppTypography.subtitle2,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Spacer(Modifier.height(12.dp))
        Divider(modifier = Modifier.fillMaxWidth(fraction = DividerWidthPercent))
        Spacer(Modifier.height(12.dp))
        Text(
            text = movie.tagline,
            style = AppTypography.body1,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Spacer(Modifier.height(12.dp))
        Divider(modifier = Modifier.fillMaxWidth(fraction = DividerWidthPercent))
        Spacer(Modifier.height(12.dp))
        Text(
            text = movie.description,
            style = AppTypography.body2,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Spacer(Modifier.height(16.dp))
        Divider(modifier = Modifier.fillMaxWidth(fraction = DividerWidthPercent))
        Spacer(Modifier.height(16.dp))
        Image(
            painter = painterResource(id = movieCommonR.drawable.ic_logo_tmdb),
            contentDescription = stringResource(movieCommonR.string.content_description_tmdb_logo),
            modifier = Modifier.fillMaxWidth(fraction = LogoWidthPercent),
            contentScale = ContentScale.Inside,
        )
    }
}

@Composable
@PreviewTheme
private fun TrendingScreenPreview(
    @PreviewParameter(MovieUiStatePreviewParameterProvider::class)
    uiState: MovieUiState,
) {
    AppTheme {
        MovieScreen(uiState = uiState)
    }
}
