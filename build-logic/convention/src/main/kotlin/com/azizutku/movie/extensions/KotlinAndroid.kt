package com.azizutku.movie.extensions

import com.android.build.api.dsl.CommonExtension
import com.azizutku.movie.AndroidConfig
import com.azizutku.movie.utils.configureGradleManagedDevices
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *>,
    optInCoroutines: Boolean = true,
) {
    commonExtension.apply {
        compileSdk = AndroidConfig.COMPILE_SDK

        defaultConfig {
            minSdk = AndroidConfig.MIN_SDK
            testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()

            // Treat all Kotlin warnings as errors (disabled by default)
            allWarningsAsErrors = true

            freeCompilerArgs = freeCompilerArgs + listOfNotNull(
                "-opt-in=kotlin.RequiresOptIn",
                "-Xcontext-receivers",
                // Enable experimental coroutines APIs, including Flow
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi".takeIf { optInCoroutines },
                "-opt-in=kotlinx.coroutines.FlowPreview".takeIf { optInCoroutines },
            )
        }
        buildFeatures {
            viewBinding = true
        }
        configureGradleManagedDevices(this)
    }
}

internal fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}
