package com.azizutku.movie.benchmark.features.watchlist

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.azizutku.movie.benchmark.scrollUiObjectDownUp
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
    device.scrollUiObjectDownUp(watchlistRecycler)
}
