package com.azizutku.movie.core.ui.extensions

import android.view.View
import android.widget.TextView
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals

class ViewExtensionsTest {
    @Test
    fun `when setVisible is called with isVisible true, should set View to VISIBLE`() {
        // Arrange
        val mockView = mockk<View>(relaxed = true)

        // Act
        mockView.setVisible(true)

        // Assert
        assertEquals(View.VISIBLE, mockView.visibility)
    }

    @Test
    fun `when setVisible is called with isVisible false and invisible true, should set View to INVISIBLE`() {
        // Arrange
        val mockView = mockk<View>(relaxed = true)

        // Act
        mockView.setVisible(false, true)

        // Assert
        verify { mockView.visibility = View.INVISIBLE }
    }

    @Test
    fun `when setVisible is called with isVisible false and invisible false, should set View to GONE`() {
        // Arrange
        val mockView = mockk<View>(relaxed = true)

        // Act
        mockView.setVisible(false)

        // Assert
        verify { mockView.visibility = View.GONE }
    }

    @Test
    fun `when setTextIfAvailableOrHide is called with non-null text, should set View to VISIBLE`() {
        // Arrange
        val mockTextView = mockk<TextView>(relaxed = true)
        val mockOtherView = mockk<View>(relaxed = true)

        // Act
        mockTextView.setTextIfAvailableOrHide("Some text", mockOtherView)

        // Assert
        verify {
            mockTextView.text = "Some text"
            mockTextView.visibility = View.VISIBLE
            mockOtherView.visibility = View.VISIBLE
        }
    }

    @Test
    fun `when setTextIfAvailableOrHide is called with null text, should set View to GONE`() {
        // Arrange
        val mockTextView = mockk<TextView>(relaxed = true)
        val mockOtherView = mockk<View>(relaxed = true)

        // Act
        mockTextView.setTextIfAvailableOrHide(null, mockOtherView)

        // Assert
        verify {
            mockTextView.text = null
            mockTextView.visibility = View.GONE
            mockOtherView.visibility = View.GONE
        }
    }
}