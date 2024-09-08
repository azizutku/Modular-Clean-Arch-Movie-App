import com.azizutku.movie.extensions.libs

plugins {
    id("movie.android.library")
    id("com.google.devtools.ksp")
    id("kotlinx-serialization")
    id("movie.android.hilt")
}

android {
    namespace = "com.azizutku.feature.trending.common"
}

dependencies {
    add("implementation", project(":core:common"))
    add("implementation", project(":core:database"))
    add("implementation", project(":core:ui"))
    add("implementation", project(":core:network"))
    add("implementation", project(":core:domain"))
    add("implementation", project(":core:model"))
    add("implementation", libs.findLibrary("coroutines").get())
    add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
    add("implementation", libs.findLibrary("retrofit").get())
    add("implementation", libs.findLibrary("timber").get())
    add("implementation", libs.findBundle("androidx.room").get())
    add("ksp", libs.findLibrary("room.compiler").get())
}
