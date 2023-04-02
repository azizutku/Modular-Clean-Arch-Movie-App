import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("movie.android.library")
                apply("movie.android.hilt")
            }
            dependencies {
                add("implementation", project(":core:common"))
                add("implementation", project(":core:database"))
                add("implementation", project(":core:ui"))
                add("implementation", project(":core:network"))
            }
        }
    }
}
