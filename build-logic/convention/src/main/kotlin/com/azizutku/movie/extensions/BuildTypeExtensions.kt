package com.azizutku.movie.extensions

import com.android.build.api.dsl.ApplicationBuildType

fun ApplicationBuildType.buildConfigStringField(name: String, value: String) {
    this.buildConfigField("String", name, "\"$value\"")
}
