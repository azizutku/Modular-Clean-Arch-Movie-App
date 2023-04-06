plugins {
    id("movie.android.library")
    id("movie.android.hilt")
    id("movie.android.room")
    id("kotlinx-serialization")
}

android {
    namespace = "com.azizutku.movie.core.database"
}

dependencies {
    implementation(libs.coroutines)
    implementation(libs.kotlinx.serialization.json)
}
