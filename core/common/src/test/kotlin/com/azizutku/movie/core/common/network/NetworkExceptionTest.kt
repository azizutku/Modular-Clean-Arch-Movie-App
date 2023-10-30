package com.azizutku.movie.core.common.network

import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class NetworkExceptionTest(
    private val exception: Exception,
    private val expectedNetworkExceptionCode: Int,
    private val expectedNetworkExceptionMessage: String?,
) {
    @Test
    fun `calling getFromThrowable with SocketTimeoutException, it returns NetworkException with correct code`() {
        // Act
        val actualNetworkException = NetworkException.getFromThrowable(exception)

        // Assert
        assertEquals(expectedNetworkExceptionCode, actualNetworkException.code)
        assertEquals(expectedNetworkExceptionMessage, actualNetworkException.message)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(
            name = "given exception {0}, expected NetworkException code: {1}, expected NetworkException message: {1}"
        )
        fun data(): Collection<Array<Any?>> {
            return listOf(
                arrayOf(SocketTimeoutException(), NetworkException.CODE_TIMEOUT_EXCEPTION, null),
                arrayOf(IOException(), NetworkException.CODE_IO_EXCEPTION, null),
                arrayOf(
                    HttpException(Response.success(null)),
                    NetworkException.CODE_HTTP_EXCEPTION,
                    null,
                ),
                arrayOf(Exception("other"), NetworkException.CODE_OTHER_EXCEPTION, "other"),
            )
        }
    }
}
