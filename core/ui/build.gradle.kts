plugins {
    id("movie.android.library")
    id("movie.android.hilt")
}

android {
    namespace = "com.azizutku.movie.core.ui"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.cardview)
    implementation(libs.material)
    implementation(libs.lottie)
    implementation(libs.androidx.constraintlayout)
}
