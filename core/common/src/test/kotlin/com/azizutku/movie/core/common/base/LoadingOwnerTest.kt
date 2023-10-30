package com.azizutku.movie.core.common.base

import app.cash.turbine.test
import com.azizutku.movie.core.common.fakes.FakeLoadingOwnerViewModel
import com.azizutku.movie.core.common.vo.DataState
import com.azizutku.movie.core.testing.util.CoroutineRule
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals

class LoadingOwnerTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var viewModel: FakeLoadingOwnerViewModel

    @Before
    fun setUp() {
        viewModel = FakeLoadingOwnerViewModel()
    }

    @Test
    fun `stateLoading goes through correct states`() = runTest {
        // Arrange
        viewModel.stateFirst.value = DataState.Loading
        viewModel.stateSecond.value = DataState.Success(0)

        launch {
            viewModel.stateLoading.test {
                // Act
                var actualValue = awaitItem()
                // Assert
                assertEquals(false, actualValue)
                // Act
                actualValue = awaitItem()
                // Assert
                assertEquals(true, actualValue)
                // Arrange
                viewModel.stateFirst.value = DataState.Success(0)
                // Act
                actualValue = awaitItem()
                // Assert
                assertEquals(false, actualValue)
            }
        }
        runCurrent()
    }
}
