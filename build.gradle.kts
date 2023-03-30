import com.azizutku.movie.utils.VersionUtils.isNonStable
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    alias(libs.plugins.ktlint)
    alias(libs.plugins.versions)
    alias(libs.plugins.hilt).apply(false)
    alias(libs.plugins.kotlin.serialization).apply(false)
    alias(libs.plugins.ksp).apply(false)
    id("movie.git.hooks").apply(false)
    id("movie.detekt").apply(false)
}

plugins.apply("movie.git.hooks")

allprojects {
    plugins.apply("movie.detekt")
    plugins.apply("org.jlleitschuh.gradle.ktlint")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}
