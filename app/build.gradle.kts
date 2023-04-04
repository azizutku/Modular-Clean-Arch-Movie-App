plugins {
    id("movie.android.application")
    id("movie.android.hilt")
    id("movie.android.application.jacoco")
}

dependencies {
    implementation(project(":feature:movie"))
    implementation(project(":feature:trending"))
    implementation(project(":feature:watchlist"))
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.splash.screen)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.hilt)
    implementation(libs.coroutines)
    implementation(libs.timber)
    implementation(libs.androidx.profileinstaller)

    testImplementation(libs.junit.test)
    testImplementation(libs.hilt.test)
    testImplementation(libs.room.test)
    testImplementation(libs.paging.test)
    testImplementation(libs.turbine.test)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.arch.core.test)

    androidTestImplementation(libs.junit.androidTest)
    androidTestImplementation(libs.espesso.androidTest)
    androidTestImplementation(libs.hilt.androidTest)
    androidTestImplementation(libs.navigation.androidTest)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.arch.core.test)
}
