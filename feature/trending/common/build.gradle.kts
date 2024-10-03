plugins {
    id("movie.android.feature.common")
}

android {
    namespace = "com.azizutku.movie.feature.trending.common"
}

dependencies {
    testImplementation(project(":feature:trending:testing"))
}