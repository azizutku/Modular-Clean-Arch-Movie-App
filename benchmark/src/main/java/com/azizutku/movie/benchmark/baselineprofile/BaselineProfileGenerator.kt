package com.azizutku.movie.benchmark.baselineprofile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.uiautomator.By
import com.azizutku.movie.benchmark.PACKAGE_NAME
import com.azizutku.movie.benchmark.features.movie.movieClickWatchlistAction
import com.azizutku.movie.benchmark.features.movie.movieScrollContentDownUp
import com.azizutku.movie.benchmark.features.movie.movieWaitForContent
import com.azizutku.movie.benchmark.features.trending.trendingScrollMoviesDownUp
import com.azizutku.movie.benchmark.features.trending.trendingWaitForContent
import com.azizutku.movie.benchmark.features.watchlist.watchlistScrollMoviesDownUp
import com.azizutku.movie.benchmark.features.watchlist.watchlistWaitForContent
import org.junit.Rule
import org.junit.Test

/**
 * Generates a baseline profile which can be copied to `app/src/main/baseline-prof.txt`.
 */
@RequiresApi(Build.VERSION_CODES.P)
class BaselineProfileGenerator {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() = baselineProfileRule.collect(PACKAGE_NAME) {
        pressHome()
        startActivityAndWait()

        trendingWaitForContent()
        trendingScrollMoviesDownUp()

        // Navigate to movie screen
        device.waitForIdle()
        val trendingRecycler = device.findObject(
            By.res(packageName, "fragment_trending_recyclerview_trending"),
        )
        trendingRecycler.children.first().click()
        device.waitForIdle()

        movieWaitForContent()
        movieClickWatchlistAction()
        movieScrollContentDownUp()

        // Navigate to watchlist screen
        device.pressBack()
        device.findObject(By.res(packageName, "nav_watchlist")).click()
        device.waitForIdle()

        watchlistWaitForContent()
        watchlistScrollMoviesDownUp()
    }
}
