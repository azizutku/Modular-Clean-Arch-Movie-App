package com.azizutku.movie.core.common.util

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.azizutku.movie.core.testing.util.CoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkClass
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test

class ThemeUtilsTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @RelaxedMockK
    lateinit var context: Context

    private lateinit var themeUtils: ThemeUtilsImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        themeUtils = ThemeUtilsImpl()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when uiMode is UI_MODE_NIGHT_YES, then isDarkTheme returns true, isLightTheme return false`() {
        // Arrange
        val expectedIsDarkTheme = true
        val expectedIsLightTheme = false
        context.resources.configuration.uiMode = Configuration.UI_MODE_NIGHT_YES

        // Act
        val actualIsDarkTheme = themeUtils.isDarkTheme(context)
        val actualIsLightTheme = themeUtils.isLightTheme(context)

        // Assert
        assertEquals(expectedIsDarkTheme, actualIsDarkTheme)
        assertEquals(expectedIsLightTheme, actualIsLightTheme)
    }

    @Test
    fun `when uiMode is UI_MODE_NIGHT_NO, then isLightTheme returns true, isDarkTheme returns false`() {
        // Arrange
        val expectedIsLightTheme = true
        val expectedIsDarkTheme = false
        context.resources.configuration.uiMode = Configuration.UI_MODE_NIGHT_NO

        // Act
        val actualIsLightTheme = themeUtils.isLightTheme(context)
        val actualIsDarkTheme = themeUtils.isDarkTheme(context)

        // Assert
        assertEquals(expectedIsDarkTheme, actualIsDarkTheme)
        assertEquals(expectedIsLightTheme, actualIsLightTheme)
    }

    @Test
    fun `calling toggleTheme should switch to light theme if currently in dark theme`() {
        // Arrange
        val expectedValue = false
        val spykThemeUtils = spyk(themeUtils)
        context.resources.configuration.uiMode = Configuration.UI_MODE_NIGHT_YES

        // Act
        spykThemeUtils.toggleTheme(context)

        // Assert
        verify(exactly = 1) { spykThemeUtils.setMode(isNightMode = expectedValue) }
    }

    @Test
    fun `calling toggleTheme should switch to dark theme if currently in light theme`() {
        // Arrange
        val expectedValue = true
        val spykThemeUtils = spyk(themeUtils)
        context.resources.configuration.uiMode = Configuration.UI_MODE_NIGHT_NO

        // Act
        spykThemeUtils.toggleTheme(context)

        // Assert
        verify(exactly = 1) { spykThemeUtils.setMode(isNightMode = expectedValue) }
    }

    @Test
    fun `calling setMode should switch to night mode after delay`() = runTest {
        // Arrange
        val delayTimeMillis = 1_000L
        mockkStatic(AppCompatDelegate::setDefaultNightMode)
        every { AppCompatDelegate.setDefaultNightMode(any()) } returns Unit

        // Act
        themeUtils.setMode(isNightMode = true, delayTimeMillis = delayTimeMillis)

        // Assert
        verify(exactly = 0) { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) }
        advanceTimeBy(delayTimeMillis * 2)
        verify(exactly = 1) { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) }
    }

    @Test
    fun `calling setMode should switch to light mode after delay`() = runTest {
        // Arrange
        val delayTimeMillis = 1_000L
        mockkStatic(AppCompatDelegate::setDefaultNightMode)
        every { AppCompatDelegate.setDefaultNightMode(any()) } returns Unit

        // Act
        themeUtils.setMode(isNightMode = false, delayTimeMillis = delayTimeMillis)

        // Assert
        verify(exactly = 0) { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) }
        advanceTimeBy(delayTimeMillis * 2)
        verify(exactly = 1) { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) }
    }
}