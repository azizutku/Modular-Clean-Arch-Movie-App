plugins {
    id("movie.android.feature.compose")
}

android {
    namespace = "com.azizutku.movie.feature.trending"
}

dependencies {
    implementation(libs.androidx.paging)
    implementation(project(":feature:trending:common"))
    testImplementation(project(":feature:trending:testing"))
}
