package com.azizutku.movie.core.common.network

import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class GeneralNetworkExceptionCodeTest(
    private val networkExceptionCode: Int,
    private val expectedGeneralNetworkExceptionCode: GeneralNetworkExceptionCode?,
) {
    @Test
    fun `calling getFromCode with NetworkException code, it returns correct GeneralNetworkExceptionCode`() {
        // Act
        val actualGeneralNetworkExceptionCode = GeneralNetworkExceptionCode.getFromCode(networkExceptionCode)

        // Assert
        assertEquals(expectedGeneralNetworkExceptionCode, actualGeneralNetworkExceptionCode)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "given NetworkException code {0}, expected GeneralNetworkExceptionCode: {1}")
        fun data(): Collection<Array<Any?>> {
            return listOf(
                arrayOf(NetworkException.CODE_PARSING_EXCEPTION, GeneralNetworkExceptionCode.PARSING_EXCEPTION),
                arrayOf(NetworkException.CODE_IO_EXCEPTION, GeneralNetworkExceptionCode.IO_EXCEPTION),
                arrayOf(NetworkException.CODE_HTTP_EXCEPTION, GeneralNetworkExceptionCode.HTTP_EXCEPTION),
                arrayOf(NetworkException.CODE_TIMEOUT_EXCEPTION, GeneralNetworkExceptionCode.TIMEOUT_EXCEPTION),
                arrayOf(NetworkException.CODE_OTHER_EXCEPTION, GeneralNetworkExceptionCode.OTHER_EXCEPTION),
                arrayOf(-1, null),
            )
        }
    }
}
