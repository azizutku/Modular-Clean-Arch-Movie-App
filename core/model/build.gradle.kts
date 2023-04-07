plugins {
    id("movie.android.library")
}

android {
    namespace = "com.azizutku.movie.core.model"
}

dependencies {
    implementation(libs.coroutines)
}
