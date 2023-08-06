import com.azizutku.movie.extensions.buildConfigStringField
import com.azizutku.movie.extensions.getLocalProperty

plugins {
    id("movie.android.library")
    id("movie.android.hilt")
}

android {
    namespace = "com.azizutku.movie.core.network"

    buildTypes.forEach {
        kotlin.runCatching {
            it.buildConfigStringField(
                com.azizutku.movie.BuildConstants.CONFIG_NAME_API_BASE_URL,
                com.azizutku.movie.BuildConstants.CONFIG_VALUE_API_BASE_URL,
            )
            it.buildConfigStringField(
                com.azizutku.movie.BuildConstants.CONFIG_NAME_API_KEY,
                getLocalProperty(com.azizutku.movie.BuildConstants.PROPERTY_NAME_API_KEY),
            )
        }.onFailure {
            throw InvalidUserDataException(com.azizutku.movie.BuildConstants.MESSAGE_API_KEY_EXCEPTION)
        }
    }

    buildFeatures.buildConfig = true
}

dependencies {
    implementation(project(":core:common"))
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.kotlinx.serialization.converter)
}
