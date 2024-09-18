plugins {
    id("movie.android.library")
    id("movie.compose")
}

android {
    namespace = "com.azizutku.movie.core.ui.compose"
}

dependencies {
    implementation(project(":core:ui:common"))
}
