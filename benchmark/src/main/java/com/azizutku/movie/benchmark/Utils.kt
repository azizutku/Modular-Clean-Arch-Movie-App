package com.azizutku.movie.benchmark

val PACKAGE_NAME = StringBuilder("com.azizutku.movie").apply {
    if (BuildConfig.FLAVOR != "prod" && BuildConfig.FLAVOR != "benchmark") {
        append(".${BuildConfig.FLAVOR}")
    }
}.toString()
