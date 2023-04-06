plugins {
    id("movie.android.application")
    id("movie.android.hilt")
    id("movie.android.room")
    id("movie.android.application.jacoco")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.splash.screen)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.viewmodel.ktx)
    implementation(libs.androidx.paging)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.material)
    implementation(libs.hilt)
    implementation(libs.coroutines)
    implementation(libs.timber)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.lottie)
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.kotlinx.serialization.converter)
    implementation(libs.glide)
    implementation(libs.androidx.profileinstaller)

    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)
    add("benchmarkImplementation", libs.chucker.no.op)

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

kapt {
    correctErrorTypes = true
}
