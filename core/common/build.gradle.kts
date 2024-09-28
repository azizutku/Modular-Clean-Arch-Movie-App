plugins {
    id("movie.android.library")
    id("movie.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.azizutku.movie.core.common"
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.coroutines)
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.bundles.androidx.navigation)
}
