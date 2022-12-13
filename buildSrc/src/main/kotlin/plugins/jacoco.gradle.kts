package plugins

import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

internal val coverageExclusions = listOf(
    // Android
    "**/R.class",
    "**/R\$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "android/databinding/**/*.class",
    "**/android/databinding/*Binding.class",
    "**/android/databinding/*",
    "**/androidx/databinding/*",
    "**/BR.*",
    "**/*Test*.*",
    "android/**/*.*",
    // kotlin
    "**/*MapperImpl*.*",
    "**/BuildConfig.*",
    "**/*Component*.*",
    "**/*BR*.*",
    "**/Manifest*.*",
    "**/*Companion*.*",
    "**/*Module*.*",
    "**/*Dagger*.*",
    "**/*Hilt*.*",
    "**/*MembersInjector*.*",
    "**/*_MembersInjector.class",
    "**/*_Factory*.*",
    "**/*_Provide*Factory*.*",
    // sealed and data classes
    "**/*$Result.*",
    "**/*$Result$*.*",
    "**/*Dto*.*",
    "**/*Entity*.*",
)

plugins {
    id("org.gradle.jacoco")
    id("com.android.application")
}

internal val jacocoTestReport = tasks.create("jacocoTestReport")

internal val extension = extensions.getByType<ApplicationAndroidComponentsExtension>()
internal val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

configure<JacocoPluginExtension> {
    toolVersion = libs.findVersion("jacocoPlugin").get().toString()
}

extension.onVariants { variant ->
    val testTaskName = "test${variant.name.capitalize()}UnitTest"
    val reportTask = tasks.register("jacoco${testTaskName.capitalize()}Report", org.gradle.testing.jacoco.tasks.JacocoReport::class) {
        dependsOn(testTaskName)
        reports {
            xml.required.set(true)
            html.required.set(true)
        }
        classDirectories.setFrom(
            fileTree("$buildDir/tmp/kotlin-classes/${variant.name}") {
                exclude(coverageExclusions)
            }
        )
        sourceDirectories.setFrom(files("$projectDir/src/main/java", "$projectDir/src/main/kotlin"))
        executionData.setFrom(file("$buildDir/jacoco/$testTaskName.exec"))
    }
    jacocoTestReport.dependsOn(reportTask)
}

tasks.withType<Test>().configureEach {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}
