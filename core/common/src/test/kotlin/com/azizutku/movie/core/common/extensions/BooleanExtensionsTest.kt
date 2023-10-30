package com.azizutku.movie.core.common.extensions

import kotlin.test.Test
import kotlin.test.assertEquals

class BooleanExtensionsTest {
    @Test
    fun `calling orFalse method on nullable Boolean that has not value, returns false`() {
        // Arrange
        val expectedResult = false
        val value: Boolean? = null

        // Act
        val actualResult = value.orFalse()

        // Assert
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `calling orFalse method on nullable Boolean that has value, returns value`() {
        // Arrange
        val expectedResult = true
        val value = true

        // Act
        val actualResult = value.orFalse()

        // Assert
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `calling orTrue method on nullable Boolean that has not value, returns true`() {
        // Arrange
        val expectedResult = true
        val value: Boolean? = null

        // Act
        val actualResult = value.orTrue()

        // Assert
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `calling orTrue method on nullable Boolean that has value, returns value`() {
        // Arrange
        val expectedResult = false
        val value = false

        // Act
        val actualResult = value.orTrue()

        // Assert
        assertEquals(expectedResult, actualResult)
    }
}
