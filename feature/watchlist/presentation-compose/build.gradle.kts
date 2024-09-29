plugins {
    id("movie.android.feature.compose")
}

android {
    namespace = "com.azizutku.movie.feature.watchlist"
}

dependencies {
    implementation(project(":feature:watchlist:common"))
}
