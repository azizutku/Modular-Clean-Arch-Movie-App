package com.azizutku.movie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.azizutku.movie.navigation.AppNavHost
import com.azizutku.movie.ui.AppBottomNavigation
import com.azizutku.movie.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val appState = rememberAppState()
            AppTheme {
                CleanMovieApp(appState)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CleanMovieApp(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    val currentDestination = appState.currentDestination
    val currentTopLevelDestination = appState.currentTopLevelDestination
    Scaffold(
        modifier = modifier.semantics { testTagsAsResourceId = true },
        bottomBar = {
            if (currentTopLevelDestination != null) {
                AppBottomNavigation(currentDestination, appState)
            }
        },
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top),
                ),
        ) {
            AppNavHost(appState = appState)
        }
    }
}

@Preview
@Composable
private fun CleanMovieAppPreview() {
    AppTheme {
        CleanMovieApp(rememberAppState())
    }
}
