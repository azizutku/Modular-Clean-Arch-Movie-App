plugins {
    id("movie.android.library")
    id("movie.android.hilt")
}

android {
    namespace = "com.azizutku.movie.core.testing"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":feature:movie"))
    implementation(project(":feature:trending"))
    implementation(project(":feature:watchlist"))

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
