import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import utils.VersionUtils.isNonStable

plugins {
    alias(libs.plugins.ktlint)
    alias(libs.plugins.versions)
    alias(libs.plugins.hilt).apply(false)
    alias(libs.plugins.kotlin.serialization).apply(false)
}

plugins.apply(BuildPlugins.GIT_HOOKS)

allprojects {
    plugins.apply(BuildPlugins.DETEKT)
    plugins.apply(BuildPlugins.KTLINT_PLUGIN)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}
