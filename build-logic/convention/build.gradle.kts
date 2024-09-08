plugins {
    `kotlin-dsl`
}

group = "com.azizutku.movie.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.plugin)
    implementation(libs.ksp.gradle.plugin)
    implementation(libs.detekt.plugin)
    implementation(libs.javapoet.plugin)
    implementation(libs.navigation.safeargs.plugin)
    implementation(libs.kotlinx.kover.plugin)
    implementation(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "movie.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidHilt") {
            id = "movie.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidRoom") {
            id = "movie.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidBenchmark") {
            id = "movie.android.benchmark"
            implementationClass = "AndroidBenchmarkConventionPlugin"
        }
        register("androidApplicationJacoco") {
            id = "movie.android.application.jacoco"
            implementationClass = "AndroidApplicationJacocoConventionPlugin"
        }
        register("androidLibraryJacoco") {
            id = "movie.android.library.jacoco"
            implementationClass = "AndroidLibraryJacocoConventionPlugin"
        }
        register("androidLibrary") {
            id = "movie.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeatureXml") {
            id = "movie.android.feature.xml"
            implementationClass = "AndroidFeatureXmlConventionPlugin"
        }
        register("androidFeatureCompose") {
            id = "movie.android.feature.compose"
            implementationClass = "AndroidFeatureComposeConventionPlugin"
        }
        register("androidFeatureCommon") {
            id = "movie.android.feature.common"
            implementationClass = "AndroidFeatureCommonConventionPlugin"
        }
        register("androidFeatureTesting") {
            id = "movie.android.feature.testing"
            implementationClass = "AndroidFeatureTestingConventionPlugin"
        }
        register("gitHooks") {
            id = "movie.git.hooks"
            implementationClass = "GitHooksConventionPlugin"
        }
        register("detekt") {
            id = "movie.detekt"
            implementationClass = "DetektConventionPlugin"
        }
        register("compose") {
            id = "movie.compose"
            implementationClass = "ComposeConventionPlugin"
        }
    }
}
