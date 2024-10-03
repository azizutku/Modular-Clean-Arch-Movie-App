plugins {
    id("movie.android.feature.compose")
}

android {
    namespace = "com.azizutku.movie.feature.trending"
}

dependencies {
    implementation(project(":feature:trending:common"))
    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
    testImplementation(project(":feature:trending:testing"))
}
