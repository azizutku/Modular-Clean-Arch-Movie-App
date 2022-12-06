package com.azizutku.movie.benchmark.features.trending

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.azizutku.movie.benchmark.PACKAGE_NAME
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TrendingBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun measureTrendingCompilationNone() = measureTrending(CompilationMode.None())

    @Test
    fun measureTrendingCompilationBaselineProfile() = measureTrending(CompilationMode.Partial())

    private fun measureTrending(compilationMode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(FrameTimingMetric()),
        compilationMode = compilationMode,
        iterations = 5,
        startupMode = StartupMode.COLD,
        setupBlock = {
            pressHome()
            startActivityAndWait()
        }
    ) {
        trendingWaitForContent()
        trendingScrollMoviesDownUp()
    }
}
