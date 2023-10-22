package com.azizutku.movie.benchmark.features.watchlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.uiautomator.By
import com.azizutku.movie.benchmark.PACKAGE_NAME
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RequiresApi(Build.VERSION_CODES.N)
@RunWith(AndroidJUnit4ClassRunner::class)
class WatchlistBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun measureWatchlistCompilationNone() = measureWatchlist(CompilationMode.None())

    @Test
    fun measureWatchlistCompilationBaselineProfile() = measureWatchlist(CompilationMode.Partial())

    private fun measureWatchlist(compilationMode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(FrameTimingMetric()),
        compilationMode = compilationMode,
        iterations = 5,
        startupMode = StartupMode.COLD,
        setupBlock = {
            pressHome()
            startActivityAndWait()

            // Navigate to watchlist screen
            device.findObject(By.res(packageName, "nav_watchlist")).click()
            device.waitForIdle()
        },
    ) {
        watchlistWaitForContent()
        watchlistScrollMoviesDownUp()
    }
}
