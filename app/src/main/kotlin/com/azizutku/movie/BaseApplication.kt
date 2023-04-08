package com.azizutku.movie

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val FORMAT_TIMBER_CREATE_STACK_ELEMENT = "%s:%s"

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.Default).launch {
            initTimber()
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(
                object : DebugTree() {
                    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                        super.log(priority, "Timber_$tag", message, t)
                    }

                    override fun createStackElementTag(element: StackTraceElement): String {
                        return String.format(
                            FORMAT_TIMBER_CREATE_STACK_ELEMENT,
                            element.methodName,
                            super.createStackElementTag(element)
                        )
                    }
                }
            )
        }
    }
}
