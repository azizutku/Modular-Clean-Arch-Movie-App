plugins {
    id("movie.android.feature")
    id("movie.android.library.jacoco")
}

android {
    namespace = "com.azizutku.movie.feature.trending"
}

dependencies {
    implementation(libs.androidx.paging)
    implementation(libs.androidx.swiperefreshlayout)
}
