package com.azizutku.movie.benchmark.features.trending

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import java.util.concurrent.TimeUnit

fun MacrobenchmarkScope.trendingWaitForContent() {
    device.wait(
        Until.hasObject(
            By.res(packageName, "fragment_trending_toolbar")
        ),
        TimeUnit.SECONDS.toMillis(30),
    )
}

fun MacrobenchmarkScope.trendingScrollMoviesDownUp() {
    val trendingRecycler = device.findObject(By.res(packageName, "fragment_trending_recyclerview_trending"))
    trendingRecycler.fling(Direction.DOWN)
    device.waitForIdle()
    trendingRecycler.fling(Direction.UP)
}
