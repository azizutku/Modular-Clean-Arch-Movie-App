package com.azizutku.movie.core.network

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultCallAdapterTest {

    private lateinit var factory: CallAdapter.Factory
    private lateinit var resultCall: ResultCall<String>

    @RelaxedMockK
    private lateinit var mockDelegate: Call<String>
    @RelaxedMockK
    private lateinit var retrofit: Retrofit

    private val annotations: Array<out Annotation> = emptyArray()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        resultCall = ResultCall(mockDelegate)
        factory = ResultCallAdapterFactory()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when returnType is not a Call, factory should return null`() {
        // Arrange
        val returnType: Type = String::class.java
        // Act
        val result = factory.get(returnType, annotations, retrofit)

        // Assert
        assertNull(result)
    }

    @Test
    fun `when returnType is a Call but not parameterized with Result, factory should return null`() {
        // Arrange
        val returnType = mockParameterizedType(Call::class.java, String::class.java)

        // Act
        val result = factory.get(returnType, annotations, retrofit)

        // Assert
        assertNull(result)
    }

    @Test
    fun `when returnType is a Call parameterized with Result, factory should return a CallAdapter`() {
        // Arrange
        val returnType = mockParameterizedType(
            Call::class.java,
            mockParameterizedType(Result::class.java, String::class.java),
        )

        // Act
        val result = factory.get(returnType, annotations, retrofit)

        // Assert
        assertNotNull(result)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun `when returnType is a Call parameterized with Result, factory should return an instance of a CallAdapter`() {
        // Arrange
        val returnType = mockParameterizedType(
            Call::class.java,
            mockParameterizedType(Result::class.java, Int::class.java),
        )
        every { returnType.rawType } returns Call::class.java

        // Act
        val result = factory.get(returnType, annotations, retrofit)

        // Assert
        assertNotNull(result)
        assertEquals(Int::class.java, result?.responseType())
        assertNotNull(result as? CallAdapter<Any, Call<Result<*>>>)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun `adapt method should return ResultCall wrapping original Call`() {
        // Arrange
        val returnType = mockParameterizedType(
            Call::class.java,
            mockParameterizedType(Result::class.java, String::class.java),
        )
        val callAdapter = factory.get(
            returnType,
            annotations,
            retrofit,
        ) as? CallAdapter<Any, Call<Result<*>>>
        val originalCall = mockk<Call<Any>>()

        // Act
        val adaptedCall = callAdapter?.adapt(originalCall)
        adaptedCall?.execute()

        // Assert
        assertNotNull(adaptedCall)
        assertTrue(adaptedCall is ResultCall<*>)
        verify(exactly = 1) { originalCall.execute() }
    }

    private fun mockParameterizedType(
        rawType: Type,
        vararg actualTypeArguments: Type,
    ): ParameterizedType {
        return mockk<ParameterizedType>().apply {
            every { this@apply.rawType } returns rawType
            every { this@apply.actualTypeArguments } returns actualTypeArguments
        }
    }
}