plugins {
    id("movie.android.feature.testing")
}

android {
    namespace = "com.azizutku.movie.core.testing"
}

dependencies {
    implementation(project(":feature:movie:presentation-xml"))
    implementation(project(":feature:trending:common"))
    implementation(project(":feature:watchlist"))
}
