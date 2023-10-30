package com.azizutku.movie.core.common.base

import app.cash.turbine.test
import com.azizutku.movie.core.common.fakes.FakeErrorOwnerViewModel
import com.azizutku.movie.core.common.network.NetworkException
import com.azizutku.movie.core.common.vo.DataState
import com.azizutku.movie.core.testing.util.CoroutineRule
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ErrorOwnerTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var viewModel: FakeErrorOwnerViewModel

    @Before
    fun setUp() {
        viewModel = FakeErrorOwnerViewModel()
    }

    @Test
    fun `when one of the flows has error state, stateError goes through error state`() = runTest {
        // Arrange
        val expectedExceptionCode = 0
        viewModel.stateFirst.value = DataState.Error(NetworkException(code = expectedExceptionCode))
        viewModel.stateSecond.value = DataState.Success(0)

        // Act
        launch {
            viewModel.stateError.test {
                // Act
                val actualExceptionCode = awaitItem().exception.code

                // Assert
                assertEquals(expectedExceptionCode, actualExceptionCode)
            }
        }
        runCurrent()
    }

    @Test
    fun `when none of the flows has error state, stateError should have no value`() = runTest {
        // Arrange
        viewModel.stateFirst.value = DataState.Success(0)
        viewModel.stateSecond.value = DataState.Success(0)

        launch {
            viewModel.stateError.test(timeout = 10.toDuration(DurationUnit.MILLISECONDS)) {
                // Act
                val exception = runCatching {
                    awaitItem()
                }.exceptionOrNull()

                // Assert
                assert(exception != null)
            }
        }
        runCurrent()
    }
}
