plugins {
    id("movie.android.library")
    id("movie.android.hilt")
}

android {
    namespace = "com.azizutku.movie.core.domain"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(libs.androidx.paging)
    implementation(libs.coroutines)
}
