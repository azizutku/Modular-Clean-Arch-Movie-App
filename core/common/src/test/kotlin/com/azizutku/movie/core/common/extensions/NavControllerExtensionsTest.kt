package com.azizutku.movie.core.common.extensions

import android.content.Context
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.test.core.app.ApplicationProvider
import com.azizutku.movie.core.common.R
import io.mockk.MockKAnnotations
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

    private lateinit var context: Context

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        context = ApplicationProvider.getApplicationContext()
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
        val deeplinkPattern = this@NavControllerExtensionsTest.context.getString(R.string.deep_link_movie)
        val argumentPattern = this@NavControllerExtensionsTest.context.getString(R.string.argument_movie_id)
        mockNavController.navigateToMovie(deeplinkPattern, argumentPattern, movieId)
    }
}
