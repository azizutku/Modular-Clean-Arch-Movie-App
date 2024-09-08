plugins {
    id("movie.android.feature.testing")
}

android {
    namespace = "com.azizutku.movie.feature.trending.testing"
}

dependencies {
    api(project(":feature:trending:common"))
}
