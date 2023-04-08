package com.azizutku.movie.benchmark.features.movie

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import java.util.concurrent.TimeUnit

fun MacrobenchmarkScope.movieWaitForContent() {
    device.wait(
        Until.hasObject(
            By.res(packageName, "fragment_movie_toolbar"),
        ),
        TimeUnit.SECONDS.toMillis(30),
    )
}

fun MacrobenchmarkScope.movieClickWatchlistAction() {
    val watchlistAction = device.findObject(By.res(packageName, "item_toolbar_watchlist_action"))
    repeat(3) {
        watchlistAction.click()
        device.waitForIdle()
    }
}

fun MacrobenchmarkScope.movieScrollDetailsDownUp() {
    val movieDetails = device.findObject(By.res(packageName, "fragment_movie_textview_details"))
    movieDetails.fling(Direction.DOWN)
    device.waitForIdle()
    movieDetails.fling(Direction.UP)
}
