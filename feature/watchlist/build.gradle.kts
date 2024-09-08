plugins {
    id("movie.android.feature.xml")
}

android {
    namespace = "com.azizutku.movie.feature.watchlist"
}

dependencies {
    implementation(libs.androidx.paging)
}
