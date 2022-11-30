plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.plugin)
    implementation(libs.detekt.plugin)
    implementation(libs.javapoet.plugin)
    implementation(libs.navigation.safeargs.plugin)
}
