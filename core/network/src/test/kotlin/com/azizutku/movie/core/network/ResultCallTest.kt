package com.azizutku.movie.core.network

import com.azizutku.movie.core.common.network.NetworkException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException
import kotlin.test.assertEquals

class ResultCallTest {

    @RelaxedMockK
    lateinit var mockDelegate: Call<String>

    @RelaxedMockK
    lateinit var mockCallback: Callback<Result<String>>

    @RelaxedMockK
    lateinit var mockResponse: Response<String>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when delegate call is successful, should return Result with success`() {
        // Arrange
        every { mockResponse.isSuccessful } returns true
        every { mockResponse.body() } returns "OK"
        every { mockResponse.code() } returns 200
        every { mockDelegate.enqueue(any()) } answers {
            firstArg<Callback<String>>().onResponse(mockDelegate, mockResponse)
        }
        val resultCall = ResultCall(mockDelegate)

        // Act
        resultCall.enqueue(mockCallback)

        // Assert
        verify {
            mockCallback.onResponse(
                resultCall,
                match {
                    it.isSuccessful && it.body() == Result.success("OK")
                },
            )
        }
    }

    @Test
    fun `when delegate call is unsuccessful with predefined error body, should return Result failure with parsed exception`() {
        // Arrange
        val errorStatusCode = 7
        val errorMessage = "Invalid API key"
        val errorResponse = """
            {
              "status_code": $errorStatusCode,
              "status_message": "$errorMessage"
            }
        """.trimIndent()
        val mockResponseBody = mockk<ResponseBody>(relaxed = true)
        every { mockResponse.isSuccessful } returns false
        every { mockResponse.errorBody() } returns mockResponseBody
        every { mockResponseBody.string() } returns errorResponse
        every { mockDelegate.enqueue(any()) } answers {
            firstArg<Callback<String>>().onResponse(mockDelegate, mockResponse)
        }
        val resultCall = ResultCall(mockDelegate)

        // Act
        resultCall.enqueue(mockCallback)

        // Assert
        verify {
            mockCallback.onResponse(
                resultCall,
                match {
                    it.body() == Result.failure<String>(
                        NetworkException(code = errorStatusCode, message = errorMessage),
                    )
                },
            )
        }
    }

    @Test
    fun `when delegate call is unsuccessful without predefined error body, should return Result failure with parsing exception code`() {
        // Arrange
        every { mockResponse.isSuccessful } returns false
        every { mockDelegate.enqueue(any()) } answers {
            firstArg<Callback<String>>().onResponse(mockDelegate, mockResponse)
        }
        val resultCall = ResultCall(mockDelegate)

        // Act
        resultCall.enqueue(mockCallback)

        // Assert
        verify {
            mockCallback.onResponse(
                resultCall,
                match {
                    it.body() == Result.failure<String>(
                        NetworkException(code = NetworkException.CODE_PARSING_EXCEPTION),
                    )
                },
            )
        }
    }

    @Test
    fun `when delegate call fails due to a network issue, should return Result failure with related NetworkException`() {
        // Arrange
        val exception = SocketTimeoutException()
        every { mockDelegate.enqueue(any()) } answers {
            firstArg<Callback<String>>().onFailure(mockDelegate, exception)
        }
        val resultCall = ResultCall(mockDelegate)

        // Act
        resultCall.enqueue(mockCallback)

        // Assert
        verify {
            mockCallback.onResponse(
                resultCall,
                match {
                    it.body() == Result.failure<String>(
                        NetworkException.getFromThrowable(exception),
                    )
                },
            )
        }
    }

    @Test
    fun `isExecuted should delegate to underlying call`() {
        // Arrange
        every { mockDelegate.isExecuted } returns true
        val resultCall = ResultCall(mockDelegate)

        // Act
        val executed = resultCall.isExecuted()

        // Assert
        assert(executed)
    }


    @Test
    fun `execute should handle successful delegate call with non-null body`() {
        // Arrange
        val expectedResult = Result.success("OK")
        every { mockDelegate.execute() } returns Response.success("OK")
        val resultCall = ResultCall(mockDelegate)

        // Act
        val response = resultCall.execute()

        // Assert
        assert(response.isSuccessful)
        assertEquals(expectedResult, response.body())
    }

    @Test
    fun `execute should handle unsuccessful delegate call with valid error body`() {
        // Arrange
        val errorResponse = mockk<ResponseBody>(relaxed = true)
        val errorStatusCode = 7
        val errorMessage = "Invalid API key"
        val errorResponseBody = """
            {
              "status_code": $errorStatusCode,
              "status_message": "$errorMessage"
            }
        """.trimIndent()
        val expectedResult = Result.failure<String>(
            NetworkException(code = errorStatusCode, message = errorMessage)
        )
        every { errorResponse.string() } returns errorResponseBody
        every { mockDelegate.execute() } returns Response.error(400, errorResponse)
        val resultCall = ResultCall(mockDelegate)

        // Act
        val response = resultCall.execute()

        // Assert
        assertEquals(expectedResult, response.body())
    }

    @Test
    fun `execute should handle unsuccessful delegate call with invalid error body`() {
        // Arrange
        val expectedResult = Result.failure<String>(
            NetworkException(code = NetworkException.CODE_PARSING_EXCEPTION)
        )
        val errorResponse = mockk<ResponseBody>(relaxed = true)
        every { errorResponse.string() } returns "invalid json"
        every { mockDelegate.execute() } returns Response.error(400, errorResponse)
        val resultCall = ResultCall(mockDelegate)

        // Act
        val response = resultCall.execute()

        // Assert
        assertEquals(expectedResult, response.body())
    }

    @Test
    fun `execute should handle exception from delegate's execute method`() {
        // Arrange
        val expectedResult = Result.failure<String>(
            NetworkException.getFromThrowable(SocketTimeoutException())
        )
        every { mockDelegate.execute() } throws SocketTimeoutException()
        val resultCall = ResultCall(mockDelegate)

        // Act
        val response = resultCall.execute()

        // Assert
        assertEquals(expectedResult, response.body())
    }

    @Test
    fun `cancel should delegate to underlying call`() {
        // Arrange
        val resultCall = ResultCall(mockDelegate)

        // Act
        resultCall.cancel()

        // Assert
        verify { mockDelegate.cancel() }
    }

    @Test
    fun `isCanceled should delegate to underlying call`() {
        // Arrange
        every { mockDelegate.isCanceled } returns true
        val resultCall = ResultCall(mockDelegate)

        // Act
        val canceled = resultCall.isCanceled()

        // Assert
        assert(canceled)
    }

    @Test
    fun `clone should create new ResultCall with cloned underlying call`() {
        // Arrange
        val clonedMockDelegate = mockk<Call<String>>(relaxed = true)
        every { mockDelegate.clone() } returns clonedMockDelegate
        val resultCall = ResultCall(mockDelegate)

        // Act
        val clonedResultCall = resultCall.clone()

        // Assert
        assert(clonedResultCall is ResultCall)
    }

    @Test
    fun `request should delegate to underlying call`() {
        // Arrange
        val request = Request.Builder().url("http://example.com").build()
        every { mockDelegate.request() } returns request
        val resultCall = ResultCall(mockDelegate)

        // Act
        val req = resultCall.request()

        // Assert
        assert(req == request)
    }

    @Test
    fun `timeout should delegate to underlying call`() {
        // Arrange
        val timeout = Timeout()
        every { mockDelegate.timeout() } returns timeout
        val resultCall = ResultCall(mockDelegate)

        // Act
        val time = resultCall.timeout()

        // Assert
        assert(time == timeout)
    }
}
