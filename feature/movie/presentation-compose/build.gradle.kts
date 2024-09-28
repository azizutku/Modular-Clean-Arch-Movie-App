plugins {
    id("movie.android.feature.compose")
}

android {
    namespace = "com.azizutku.movie.feature.movie"
}

dependencies {
    implementation(project(":feature:movie:common"))
}
