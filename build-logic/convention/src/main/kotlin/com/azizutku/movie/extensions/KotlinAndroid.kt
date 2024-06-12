package com.azizutku.movie.extensions

import com.android.build.api.dsl.CommonExtension
import com.azizutku.movie.AndroidConfig
import com.azizutku.movie.utils.configureGradleManagedDevices
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    optInCoroutines: Boolean = true,
) {
    commonExtension.apply {
        compileSdk = AndroidConfig.COMPILE_SDK

        defaultConfig {
            minSdk = AndroidConfig.MIN_SDK
            testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        configureKotlin(optInCoroutines)
        buildFeatures {
            viewBinding = true
        }
        configureGradleManagedDevices(this)
    }
}

private fun Project.configureKotlin(optInCoroutines: Boolean) {
    configure<KotlinAndroidProjectExtension> {
        compilerOptions.apply {
            jvmTarget.set(JvmTarget.JVM_17)
            // Treat all Kotlin warnings as errors (disabled by default)
            allWarningsAsErrors.set(true)
            freeCompilerArgs.addAll(
                listOfNotNull(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-Xcontext-receivers",
                    // Enable experimental coroutines APIs, including Flow
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi".takeIf { optInCoroutines },
                    "-opt-in=kotlinx.coroutines.FlowPreview".takeIf { optInCoroutines },
                )
            )
        }
    }
}
