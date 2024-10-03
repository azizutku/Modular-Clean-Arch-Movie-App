package com.azizutku.movie

import android.app.Application
import android.content.pm.ApplicationInfo
import com.azizutku.movie.core.common.di.Dispatcher
import com.azizutku.movie.core.common.di.MovieAppDispatchers.Default
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val FORMAT_TIMBER_CREATE_STACK_ELEMENT = "%s:%s"

@HiltAndroidApp
class BaseApplication : Application() {

    @Inject
    @Dispatcher(Default)
    lateinit var defaultDispatcher: CoroutineDispatcher

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(defaultDispatcher).launch {
            initTimber()
        }
    }

    private fun initTimber() {
        val isDebuggable = applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        if (isDebuggable) {
            Timber.plant(
                object : DebugTree() {
                    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                        super.log(priority, "Timber_$tag", message, t)
                    }

                    override fun createStackElementTag(element: StackTraceElement): String {
                        return String.format(
                            FORMAT_TIMBER_CREATE_STACK_ELEMENT,
                            element.methodName,
                            super.createStackElementTag(element),
                        )
                    }
                },
            )
        }
    }
}
