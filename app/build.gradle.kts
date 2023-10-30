plugins {
    id("movie.android.application")
    id("movie.android.hilt")
    id("movie.android.application.jacoco")
    id("movie.android.application.kover")
}

dependencies {
    implementation(project(":feature:movie"))
    implementation(project(":feature:trending"))
    implementation(project(":feature:watchlist"))
    implementation(project(":core:common"))
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.splash.screen)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.hilt)
    implementation(libs.coroutines)
    implementation(libs.timber)
    implementation(libs.androidx.profileinstaller)
    api(libs.kotlinx.serialization.json)
}
