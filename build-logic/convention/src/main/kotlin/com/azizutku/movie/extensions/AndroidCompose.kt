package com.azizutku.movie.extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            add("implementation", platform(libs.findLibrary("androidx.compose.bom").get()))
            add("implementation", libs.findBundle("compose").get())
            add("androidTestImplementation", platform(libs.findLibrary("androidx.compose.bom").get()))
            add("androidTestImplementation", libs.findLibrary("androidx.ui.test.junit4.androidTest").get())
            add("debugImplementation", libs.findLibrary("androidx.ui.tooling.debugOnly").get())
            add("debugImplementation", libs.findLibrary("androidx.ui.test.manifest.debugOnly").get())
            add("detektPlugins", libs.findLibrary("detekt.compose.rules").get())
        }

        testOptions {
            unitTests {
                // For Robolectric
                isIncludeAndroidResources = true
            }
        }
    }

    extensions.configure<ComposeCompilerGradlePluginExtension> {
        fun Provider<String>.onlyIfTrue() = flatMap { provider { it.takeIf(String::toBoolean) } }
        fun Provider<*>.relativeToRootProject(dir: String) = flatMap {
            rootProject.layout.buildDirectory.dir(projectDir.toRelativeString(rootDir))
        }.map { it.dir(dir) }

        project.providers.gradleProperty("enableComposeCompilerMetrics").onlyIfTrue()
            .relativeToRootProject("compose-metrics")
            .let(metricsDestination::set)

        project.providers.gradleProperty("enableComposeCompilerReports").onlyIfTrue()
            .relativeToRootProject("compose-reports")
            .let(reportsDestination::set)

        stabilityConfigurationFile.set(rootProject.layout.projectDirectory.file("compose_compiler_config.conf"))
        enableStrongSkippingMode.set(true)
        includeSourceInformation.set(true)
    }
}