package com.azizutku.movie.core.common.data.repository

import app.cash.turbine.test
import com.azizutku.movie.core.model.DarkThemeConfig
import com.azizutku.movie.core.testing.util.CoroutineRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
class UserDataRepositoryImplTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var userDataRepository: UserDataRepositoryImpl

    @Before
    fun setUp() {
        userDataRepository = UserDataRepositoryImpl()
    }

    @Test
    fun `initial userData should have Light theme configuration`() = runTest {
        // Act & Assert
        userDataRepository.userData.test {
            val userData = awaitItem()
            assertEquals(DarkThemeConfig.LIGHT, userData.darkThemeConfig)
        }
    }

    @Test
    fun `calling toggleDarkThemeConfig should switch from Light to Dark theme`() = runTest {
        // Arrange
        userDataRepository.userData.test {
            assertEquals(DarkThemeConfig.LIGHT, awaitItem().darkThemeConfig)

            // Act
            userDataRepository.toggleDarkThemeConfig()

            // Assert
            assertEquals(DarkThemeConfig.DARK, awaitItem().darkThemeConfig)
        }
    }

    @Test
    fun `calling toggleDarkThemeConfig should switch from Dark to Light theme`() = runTest {
        // Arrange
        userDataRepository.toggleDarkThemeConfig() // Switch to Dark first

        userDataRepository.userData.test {
            assertEquals(DarkThemeConfig.DARK, awaitItem().darkThemeConfig)

            // Act
            userDataRepository.toggleDarkThemeConfig()

            // Assert
            assertEquals(DarkThemeConfig.LIGHT, awaitItem().darkThemeConfig)
        }
    }

    @Test
    fun `calling setDarkThemeConfig should set the theme to the provided value`() = runTest {
        // Act
        userDataRepository.setDarkThemeConfig(DarkThemeConfig.DARK)

        // Assert
        userDataRepository.userData.test {
            assertEquals(DarkThemeConfig.DARK, awaitItem().darkThemeConfig)
        }

        // Act again
        userDataRepository.setDarkThemeConfig(DarkThemeConfig.LIGHT)

        // Assert again
        userDataRepository.userData.test {
            assertEquals(DarkThemeConfig.LIGHT, awaitItem().darkThemeConfig)
        }
    }
}
