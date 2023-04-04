plugins {
    id("movie.android.feature")
}

android {
    namespace = "com.azizutku.movie.feature.watchlist"
}

dependencies {
    implementation(libs.androidx.paging)
}
