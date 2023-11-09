import com.azizutku.movie.utils.VersionUtils.isNonStable
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    alias(libs.plugins.ktlint)
    alias(libs.plugins.versions)
    alias(libs.plugins.hilt).apply(false)
    alias(libs.plugins.kotlin.serialization).apply(false)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.kotlin.jvm).apply(false)
    alias(libs.plugins.kotlinx.kover).apply(false)
    alias(libs.plugins.jacoco.aggregate.coverage).apply(true)
    id("movie.git.hooks").apply(false)
    id("movie.detekt").apply(false)
    // Required for gradle-versions-plugin as of Gradle 8.4.
    // TODO: Remove once the plugin issue is resolved.
    id("jvm-ecosystem")
}

plugins.apply("movie.git.hooks")

allprojects {
    plugins.apply("movie.detekt")
    plugins.apply("org.jlleitschuh.gradle.ktlint")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

jacocoAggregateCoverage {
    jacocoTestReportTask.set("jacocoTestDevDebugUnitTestReport")
}
