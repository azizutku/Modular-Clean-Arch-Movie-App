plugins {
    id("movie.android.application")
    id("movie.android.hilt")
    id("movie.android.application.jacoco")
    id("movie.compose")
}

dependencies {
    implementation(project(":feature:trending:presentation-compose"))
    implementation(project(":core:common"))
    implementation(project(":core:ui:compose"))
    implementation(libs.androidx.splash.screen)
    implementation(libs.hilt)
    implementation(libs.coroutines)
    implementation(libs.timber)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.kotlinx.serialization.json)
}
