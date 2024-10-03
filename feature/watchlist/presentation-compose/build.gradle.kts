plugins {
    id("movie.android.feature.compose")
}

android {
    namespace = "com.azizutku.movie.feature.watchlist"
}

dependencies {
    implementation(project(":feature:watchlist:common"))
    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
}
