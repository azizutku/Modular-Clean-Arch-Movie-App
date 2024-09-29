plugins {
    id("movie.android.feature.xml")
}

android {
    namespace = "com.azizutku.movie.feature.watchlist"
}

dependencies {
    implementation(project(":feature:watchlist:common"))
    implementation(libs.androidx.paging)
}
