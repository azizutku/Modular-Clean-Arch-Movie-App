plugins {
    id("movie.android.feature.xml")
}

android {
    namespace = "com.azizutku.movie.feature.trending"
}

dependencies {
    implementation(libs.androidx.paging)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(project(":feature:trending:common"))
    testImplementation(project(":feature:trending:testing"))
}
