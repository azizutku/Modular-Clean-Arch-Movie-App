package com.azizutku.movie.benchmark.features.movie

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

@RunWith(AndroidJUnit4ClassRunner::class)
class MovieBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun measureMovieCompilationNone() = measureMovie(CompilationMode.None())

    @Test
    fun measureMovieCompilationBaselineProfile() = measureMovie(CompilationMode.Partial())

    private fun measureMovie(compilationMode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(FrameTimingMetric()),
        compilationMode = compilationMode,
        iterations = 5,
        startupMode = StartupMode.COLD,
        setupBlock = {
            pressHome()
            startActivityAndWait()

            // Navigate to movie screen
            val trendingRecycler = device.findObject(
                By.res(packageName, "fragment_trending_recyclerview_trending"),
            )
            trendingRecycler.children.first().click()
        },
    ) {
        movieWaitForContent()
        movieClickWatchlistAction()
        movieScrollDetailsDownUp()
    }
}
