package com.azizutku.movie.benchmark.features.watchlist

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import java.util.concurrent.TimeUnit

fun MacrobenchmarkScope.watchlistWaitForContent() {
    device.wait(
        Until.hasObject(
            By.res(packageName, "fragment_watchlist_toolbar"),
        ),
        TimeUnit.SECONDS.toMillis(30),
    )
}

fun MacrobenchmarkScope.watchlistScrollMoviesDownUp() {
    val watchlistRecycler = device.findObject(By.res(packageName, "fragment_watchlist_recyclerview_movies"))
    watchlistRecycler.setGestureMargin(device.displayWidth / 5)
    watchlistRecycler.fling(Direction.DOWN)
    device.waitForIdle()
    watchlistRecycler.fling(Direction.UP)
}
