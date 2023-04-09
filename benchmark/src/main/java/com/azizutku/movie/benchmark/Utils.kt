package com.azizutku.movie.benchmark

import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2

private const val SCROLL_PERCENT = 2000f

val PACKAGE_NAME = StringBuilder("com.azizutku.movie").apply {
    if (BuildConfig.FLAVOR != "prod" && BuildConfig.FLAVOR != "benchmark") {
        append(".${BuildConfig.FLAVOR}")
    }
}.toString()

fun UiDevice.scrollUiObjectDownUp(uiObject: UiObject2) {
    // Leave some space from the edges to avoid triggering the system's navigation.
    uiObject.setGestureMargin(displayWidth / 5)

    // Fling gesture sometimes clicks items instead of flinging. Use scroll method instead.
    uiObject.scroll(Direction.DOWN, SCROLL_PERCENT)
    waitForIdle()
    uiObject.scroll(Direction.UP, SCROLL_PERCENT)
}
