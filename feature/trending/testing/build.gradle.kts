plugins {
    id("movie.android.library")
    id("movie.android.hilt")
}

android {
    namespace = "com.azizutku.movie.feature.trending.testing"
}

dependencies {
    api(project(":feature:trending:common"))
    api(project(":core:database"))
    api(libs.junit.test)
    api(libs.hilt.test)
    api(libs.room.test)
    api(libs.paging.common.test)
    api(libs.paging.test)
    api(libs.turbine.test)
    api(libs.coroutines.test)
    api(libs.arch.core.test)
    api(libs.mockkAndroid.test)
    api(libs.mockkAgent.test)
    api(libs.roboelectric.test)
    api(libs.junit.androidTest)
    api(libs.espesso.androidTest)
    api(libs.hilt.androidTest)
}
