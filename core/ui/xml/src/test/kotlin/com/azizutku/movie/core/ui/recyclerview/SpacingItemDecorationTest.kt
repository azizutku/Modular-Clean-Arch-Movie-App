package com.azizutku.movie.core.ui.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SpacingItemDecorationTest {

    private lateinit var spacingItemDecoration: SpacingItemDecoration
    private lateinit var outRect: Rect

    @MockK
    private lateinit var view: View

    @MockK
    private lateinit var parent: RecyclerView

    @MockK
    private lateinit var state: RecyclerView.State

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        outRect = Rect()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when GridLayoutManager is used, includeEdge is true and position is top end, getItemOffsets should set offsets correctly`() {
        // Arrange
        val gridLayoutManager = mockk<GridLayoutManager>()
        every { parent.layoutManager } returns gridLayoutManager
        every { gridLayoutManager.spanCount } returns 2
        every { parent.getChildAdapterPosition(view) } returns 1
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = true)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(5, outRect.left)
        assertEquals(10, outRect.right)
        assertEquals(10, outRect.bottom)
        assertEquals(10, outRect.top)
    }

    @Test
    fun `when GridLayoutManager is used, includeEdge is true and position is top start, getItemOffsets should set offsets correctly`() {
        // Arrange
        val gridLayoutManager = mockk<GridLayoutManager>()
        every { parent.layoutManager } returns gridLayoutManager
        every { gridLayoutManager.spanCount } returns 2
        every { parent.getChildAdapterPosition(view) } returns 0
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = true)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(10, outRect.left)
        assertEquals(5, outRect.right)
        assertEquals(10, outRect.bottom)
        assertEquals(10, outRect.top)
    }

    @Test
    fun `when GridLayoutManager is used, includeEdge is true and position is first of last row, getItemOffsets should set offsets correctly`() {
        // Arrange
        val gridLayoutManager = mockk<GridLayoutManager>()
        every { parent.layoutManager } returns gridLayoutManager
        every { gridLayoutManager.spanCount } returns 2
        every { parent.getChildAdapterPosition(view) } returns 4
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = true)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(10, outRect.left)
        assertEquals(5, outRect.right)
        assertEquals(10, outRect.bottom)
        assertEquals(0, outRect.top)
    }

    @Test
    fun `when GridLayoutManager is used, includeEdge is false and position is top end, getItemOffsets should set offsets correctly`() {
        // Arrange
        val gridLayoutManager = mockk<GridLayoutManager>()
        every { parent.layoutManager } returns gridLayoutManager
        every { gridLayoutManager.spanCount } returns 2
        every { parent.getChildAdapterPosition(view) } returns 1
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = false)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(5, outRect.left)
        assertEquals(0, outRect.right)
        assertEquals(0, outRect.top)
        assertEquals(0, outRect.bottom)
    }

    @Test
    fun `when GridLayoutManager is used, includeEdge is false and position is top start, getItemOffsets should set offsets correctly`() {
        // Arrange
        val gridLayoutManager = mockk<GridLayoutManager>()
        every { parent.layoutManager } returns gridLayoutManager
        every { gridLayoutManager.spanCount } returns 2
        every { parent.getChildAdapterPosition(view) } returns 0
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = false)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(0, outRect.left)
        assertEquals(5, outRect.right)
        assertEquals(0, outRect.top)
        assertEquals(0, outRect.bottom)
    }

    @Test
    fun `when GridLayoutManager is used, includeEdge is false and position is first of last row, getItemOffsets should set offsets correctly`() {
        // Arrange
        val gridLayoutManager = mockk<GridLayoutManager>()
        every { parent.layoutManager } returns gridLayoutManager
        every { gridLayoutManager.spanCount } returns 2
        every { parent.getChildAdapterPosition(view) } returns 4
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = false)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(0, outRect.left)
        assertEquals(5, outRect.right)
        assertEquals(10, outRect.top)
        assertEquals(0, outRect.bottom)
    }

    @Test
    fun `when vertical LinearLayoutManager is used, includeEdge is true and position is start, getItemOffsets should set offsets correctly`() {
        // Arrange
        val linearLayoutManager = mockk<LinearLayoutManager>()
        every { parent.layoutManager } returns linearLayoutManager
        every { linearLayoutManager.canScrollVertically() } returns true
        every { parent.getChildAdapterPosition(view) } returns 0
        every { state.itemCount } returns 6
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = true)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(0, outRect.left)
        assertEquals(0, outRect.right)
        assertEquals(10, outRect.top)
        assertEquals(10, outRect.bottom)
    }

    @Test
    fun `when vertical LinearLayoutManager is used, includeEdge is true and position is middle, getItemOffsets should set offsets correctly`() {
        // Arrange
        val linearLayoutManager = mockk<LinearLayoutManager>()
        every { parent.layoutManager } returns linearLayoutManager
        every { linearLayoutManager.canScrollVertically() } returns true
        every { parent.getChildAdapterPosition(view) } returns 1
        every { state.itemCount } returns 6
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = true)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(0, outRect.left)
        assertEquals(0, outRect.right)
        assertEquals(0, outRect.top)
        assertEquals(10, outRect.bottom)
    }

    @Test
    fun `when vertical LinearLayoutManager is used, includeEdge is true and position is end, getItemOffsets should set offsets correctly`() {
        // Arrange
        val linearLayoutManager = mockk<LinearLayoutManager>()
        every { parent.layoutManager } returns linearLayoutManager
        every { linearLayoutManager.canScrollVertically() } returns true
        every { parent.getChildAdapterPosition(view) } returns 5
        every { state.itemCount } returns 6
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = true)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(0, outRect.left)
        assertEquals(0, outRect.right)
        assertEquals(0, outRect.top)
        assertEquals(10, outRect.bottom)
    }

    @Test
    fun `when vertical LinearLayoutManager is used, includeEdge is false and position is start, getItemOffsets should set offsets correctly`() {
        // Arrange
        val linearLayoutManager = mockk<LinearLayoutManager>()
        every { parent.layoutManager } returns linearLayoutManager
        every { linearLayoutManager.canScrollVertically() } returns true
        every { parent.getChildAdapterPosition(view) } returns 0
        every { state.itemCount } returns 6
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = false)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(0, outRect.left)
        assertEquals(0, outRect.right)
        assertEquals(0, outRect.top)
        assertEquals(10, outRect.bottom)
    }

    @Test
    fun `when vertical LinearLayoutManager is used, includeEdge is false and position is middle, getItemOffsets should set offsets correctly`() {
        // Arrange
        val linearLayoutManager = mockk<LinearLayoutManager>()
        every { parent.layoutManager } returns linearLayoutManager
        every { linearLayoutManager.canScrollVertically() } returns true
        every { parent.getChildAdapterPosition(view) } returns 0
        every { state.itemCount } returns 6
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = false)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(0, outRect.left)
        assertEquals(0, outRect.right)
        assertEquals(0, outRect.top)
        assertEquals(10, outRect.bottom)
    }

    @Test
    fun `when vertical LinearLayoutManager is used, includeEdge is false and position is end, getItemOffsets should set offsets correctly`() {
        // Arrange
        val linearLayoutManager = mockk<LinearLayoutManager>()
        every { parent.layoutManager } returns linearLayoutManager
        every { linearLayoutManager.canScrollVertically() } returns true
        every { parent.getChildAdapterPosition(view) } returns 5
        every { state.itemCount } returns 6
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = false)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(0, outRect.left)
        assertEquals(0, outRect.right)
        assertEquals(0, outRect.top)
        assertEquals(0, outRect.bottom)
    }

    // ff

    @Test
    fun `when horizontal LinearLayoutManager is used, includeEdge is true and position is start, getItemOffsets should set offsets correctly`() {
        // Arrange
        val linearLayoutManager = mockk<LinearLayoutManager>()
        every { parent.layoutManager } returns linearLayoutManager
        every { linearLayoutManager.canScrollVertically() } returns false
        every { parent.getChildAdapterPosition(view) } returns 0
        every { state.itemCount } returns 6
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = true)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(10, outRect.left)
        assertEquals(10, outRect.right)
        assertEquals(0, outRect.bottom)
        assertEquals(0, outRect.top)
    }

    @Test
    fun `when horizontal LinearLayoutManager is used, includeEdge is true and position is middle, getItemOffsets should set offsets correctly`() {
        // Arrange
        val linearLayoutManager = mockk<LinearLayoutManager>()
        every { parent.layoutManager } returns linearLayoutManager
        every { linearLayoutManager.canScrollVertically() } returns false
        every { parent.getChildAdapterPosition(view) } returns 1
        every { state.itemCount } returns 6
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = true)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(0, outRect.left)
        assertEquals(10, outRect.right)
        assertEquals(0, outRect.top)
        assertEquals(0, outRect.bottom)
    }

    @Test
    fun `when horizontal LinearLayoutManager is used, includeEdge is true and position is end, getItemOffsets should set offsets correctly`() {
        // Arrange
        val linearLayoutManager = mockk<LinearLayoutManager>()
        every { parent.layoutManager } returns linearLayoutManager
        every { linearLayoutManager.canScrollVertically() } returns false
        every { parent.getChildAdapterPosition(view) } returns 5
        every { state.itemCount } returns 6
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = true)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(0, outRect.left)
        assertEquals(10, outRect.right)
        assertEquals(0, outRect.top)
        assertEquals(0, outRect.bottom)
    }


    @Test
    fun `when horizontal LinearLayoutManager is used, includeEdge is false and position is start, getItemOffsets should set offsets correctly`() {
        // Arrange
        val linearLayoutManager = mockk<LinearLayoutManager>()
        every { parent.layoutManager } returns linearLayoutManager
        every { linearLayoutManager.canScrollVertically() } returns false
        every { parent.getChildAdapterPosition(view) } returns 0
        every { state.itemCount } returns 6
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = false)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(0, outRect.left)
        assertEquals(10, outRect.right)
        assertEquals(0, outRect.top)
        assertEquals(0, outRect.bottom)
    }

    @Test
    fun `when horizontal LinearLayoutManager is used, includeEdge is false and position is middle, getItemOffsets should set offsets correctly`() {
        // Arrange
        val linearLayoutManager = mockk<LinearLayoutManager>()
        every { parent.layoutManager } returns linearLayoutManager
        every { linearLayoutManager.canScrollVertically() } returns false
        every { parent.getChildAdapterPosition(view) } returns 1
        every { state.itemCount } returns 6
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = false)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(0, outRect.left)
        assertEquals(10, outRect.right)
        assertEquals(0, outRect.top)
        assertEquals(0, outRect.bottom)
    }

    @Test
    fun `when horizontal LinearLayoutManager is used, includeEdge is false and position is end, getItemOffsets should set offsets correctly`() {
        // Arrange
        val linearLayoutManager = mockk<LinearLayoutManager>()
        every { parent.layoutManager } returns linearLayoutManager
        every { linearLayoutManager.canScrollVertically() } returns false
        every { parent.getChildAdapterPosition(view) } returns 5
        every { state.itemCount } returns 6
        spacingItemDecoration = SpacingItemDecoration(spacingPx = 10, includeEdge = false)

        // Act
        spacingItemDecoration.getItemOffsets(outRect, view, parent, state)

        // Assert
        assertEquals(0, outRect.left)
        assertEquals(0, outRect.right)
        assertEquals(0, outRect.top)
        assertEquals(0, outRect.bottom)
    }
}