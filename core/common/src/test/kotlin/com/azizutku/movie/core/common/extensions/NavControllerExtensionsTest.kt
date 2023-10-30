package com.azizutku.movie.core.common.extensions

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import com.azizutku.movie.core.common.R
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE)
class NavControllerExtensionsTest {

    @MockK
    private lateinit var mockNavController: NavController
    @MockK
    private lateinit var mockFragment: Fragment

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { mockFragment.getString(R.string.deep_link_movie) } returns "movieapp://movie/{movieId}"
        every { mockFragment.getString(R.string.argument_movie_id) } returns "movieId"
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when navigateToMovie is called, it should invoke navigate on NavController with correct URI`() {
        // Arrange
        val movieId = 42
        val expectedUri = Uri.parse("movieapp://movie/$movieId")

        // Act
        mockFragment.navigateToMovie(movieId)

        // Assert
        verify {
            mockNavController.navigate(
                withArg<NavDeepLinkRequest> {
                    assert(it.uri == expectedUri)
                },
                any(NavOptions::class)
            )
        }
    }

    private fun Fragment.navigateToMovie(movieId: Int) {
        mockNavController.navigateToMovie(movieId)
    }
}
