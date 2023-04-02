plugins {
    id("movie.android.library")
    id("movie.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.azizutku.movie.core.common"
}

dependencies {
    implementation(libs.coroutines)
    implementation(libs.androidx.recyclerview)
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.androidx.appcompat)
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
}
