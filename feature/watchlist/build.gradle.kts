plugins {
    id("movie.android.feature")
    id("movie.android.library.jacoco")
}

android {
    namespace = "com.azizutku.movie.feature.watchlist"
}

dependencies {
    implementation(libs.androidx.paging)
}