import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import utils.VersionUtils.isNonStable

plugins {
    id(BuildPlugins.KTLINT_PLUGIN).version(BuildPlugins.KTLINT_PLUGIN_VERSION)
    id(BuildPlugins.VERSIONS_PLUGIN).version(BuildPlugins.VERSIONS_PLUGIN_VERSION)
    id(BuildPlugins.HILT_PLUGIN).version(BuildPlugins.HILT_PLUGIN_VERSION).apply(false)
    id(BuildPlugins.KOTLIN_SERIALIZATION_PLUGIN).version(BuildPlugins.KOTLIN_SERIALIZATION_PLUGIN_VERSION)
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
