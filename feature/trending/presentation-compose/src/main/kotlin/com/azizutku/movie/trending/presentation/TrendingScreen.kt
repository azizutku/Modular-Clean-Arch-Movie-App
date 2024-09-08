package com.azizutku.movie.trending.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.azizutku.movie.feature.trending.common.presentation.TrendingViewModel

@Composable
fun TrendingScreen(
    modifier: Modifier = Modifier,
    viewModel: TrendingViewModel = hiltViewModel(),
) {
    viewModel.uiState.collectAsStateWithLifecycle()
    Text(modifier = modifier, text = "It is trending screen: ")
}
