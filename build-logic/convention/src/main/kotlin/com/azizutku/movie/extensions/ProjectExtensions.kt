package com.azizutku.movie.extensions

import com.azizutku.movie.utils.getLocalProperty
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

const val JDK_VERSION = 17

fun Project.getLocalProperty(propertyName: String): String {
    return getLocalProperty(propertyName, this)
}

internal fun Project.kotlin(configure: Action<KotlinAndroidProjectExtension>): Unit =
    (this as ExtensionAware).extensions.configure("kotlin", configure)
