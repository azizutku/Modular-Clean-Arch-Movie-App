allprojects {
    plugins.apply(BuildPlugins.DETEKT)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
