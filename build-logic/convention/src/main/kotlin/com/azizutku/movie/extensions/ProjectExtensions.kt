package com.azizutku.movie.extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.azizutku.movie.utils.getLocalProperty
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

const val JDK_VERSION = 17

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.getLocalProperty(propertyName: String): String {
    return getLocalProperty(propertyName, this)
}

internal fun Project.getCommonExtension(): CommonExtension<*, *, *, *, *, *> {
    return when {
        plugins.hasPlugin("com.android.application") -> extensions.getByType<ApplicationExtension>()
        plugins.hasPlugin("com.android.library") -> extensions.getByType<LibraryExtension>()
        else -> throw IllegalArgumentException("This method should be called from an Android application or library project.")
    }
}

internal fun Project.kotlin(configure: Action<KotlinAndroidProjectExtension>): Unit =
    (this as ExtensionAware).extensions.configure("kotlin", configure)
