package com.azizutku.movie.core.ui.util

import android.content.Context
import com.azizutku.movie.core.common.network.NetworkException
import com.azizutku.movie.core.ui.dialogs.AlertDialog
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import kotlin.test.Test

class ErrorHandlerTest {

    lateinit var errorHandler: ErrorHandlerImpl

    @RelaxedMockK
    lateinit var alertDialog: AlertDialog

    @RelaxedMockK
    lateinit var context: Context

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        errorHandler = ErrorHandlerImpl(context, alertDialog)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when exceptions handled in before general exception calling, handleException should not call handleGeneralException and handleAfterGeneralException methods`() {
        // Arrange
        val expectedResult = true
        val spykedErrorHandler = spyk(errorHandler)
        val handleBeforeGeneralException = mockk<(NetworkException) -> Boolean> {
            every { this@mockk.invoke(any()) } returns true
        }
        spykedErrorHandler.handleBeforeGeneralException = handleBeforeGeneralException

        // Act
        val isErrorHandled = spykedErrorHandler.handleException(NetworkException(code = 0))

        // Assert
        assertEquals(expectedResult, isErrorHandled)
        verify(exactly = 1) { handleBeforeGeneralException(any()) }
        verify(exactly = 0) { spykedErrorHandler.handleGeneralException(any()) }
        verify(exactly = 0) { spykedErrorHandler.handleAfterGeneralException(any()) }
    }

    @Test
    fun `when exceptions handled in general exception, calling handleException should not call handleAfterGeneralException methods`() {
        // Arrange
        val expectedResult = true
        val spykedErrorHandler = spyk(errorHandler)
        val handleBeforeGeneralException = mockk<(NetworkException) -> Boolean> {
            every { this@mockk.invoke(any()) } returns false
        }
        spykedErrorHandler.handleBeforeGeneralException = handleBeforeGeneralException
        every { spykedErrorHandler.handleGeneralException(any()) } returns true

        // Act
        val isErrorHandled = spykedErrorHandler.handleException(NetworkException(code = 0))

        // Assert
        assertEquals(expectedResult, isErrorHandled)
        verify(exactly = 1) { handleBeforeGeneralException(any()) }
        verify(exactly = 1) { spykedErrorHandler.handleGeneralException(any()) }
        verify(exactly = 0) { spykedErrorHandler.handleAfterGeneralException(any()) }
    }

    @Test
    fun `when exceptions are not handled until handleAfterGeneralException, calling handleException should return the same as handleAfterGeneralException`() {
        // Arrange
        val expectedResult = true
        val spykedErrorHandler = spyk(errorHandler)
        val handleBeforeGeneralException = mockk<(NetworkException) -> Boolean> {
            every { this@mockk.invoke(any()) } returns false
        }
        val handleAfterGeneralException = mockk<(NetworkException) -> Boolean> {
            every { this@mockk.invoke(any()) } returns expectedResult
        }
        spykedErrorHandler.handleBeforeGeneralException = handleBeforeGeneralException
        every { spykedErrorHandler.handleGeneralException(any()) } returns false
        spykedErrorHandler.handleAfterGeneralException = handleAfterGeneralException

        // Act
        val isErrorHandled = spykedErrorHandler.handleException(NetworkException(code = 0))

        // Assert
        assertEquals(expectedResult, isErrorHandled)
        verify(exactly = 1) { handleBeforeGeneralException(any()) }
        verify(exactly = 1) { spykedErrorHandler.handleGeneralException(any()) }
        verify(exactly = 1) { handleAfterGeneralException(any()) }
    }
}
