plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

object PluginsVersions {
    const val GRADLE_ANDROID = "7.3.1"
    const val KOTLIN = "1.7.10"
    const val DETEKT = "1.19.0"
    const val JAVAPOET = "1.13.0"
    const val NAVIGATION = "2.5.3"
}

dependencies {
    implementation("com.android.tools.build:gradle:${PluginsVersions.GRADLE_ANDROID}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginsVersions.DETEKT}")
    implementation("com.squareup:javapoet:${PluginsVersions.JAVAPOET}")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:${PluginsVersions.NAVIGATION}")
}
