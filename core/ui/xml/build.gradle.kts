plugins {
    id("movie.android.library")
    id("movie.android.hilt")
}

android {
    namespace = "com.azizutku.movie.core.ui.xml"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.cardview)
    implementation(libs.material)
    implementation(libs.lottie)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.bundles.androidx.lifecycle)
}