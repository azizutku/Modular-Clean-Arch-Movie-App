plugins {
    id(BuildPlugins.KTLINT_PLUGIN).version(BuildPlugins.KTLINT_PLUGIN_VERSION)
}

plugins.apply(BuildPlugins.GIT_HOOKS)

allprojects {
    plugins.apply(BuildPlugins.DETEKT)
    plugins.apply(BuildPlugins.KTLINT_PLUGIN)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
