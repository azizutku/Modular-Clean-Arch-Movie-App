import com.azizutku.movie.BuildPlugins
import com.azizutku.movie.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply(BuildPlugins.COMPOSE)
                apply(BuildPlugins.KSP)
                apply(BuildPlugins.KOTLINX_SERIALIZATION)
                apply("movie.android.library")
                apply("movie.android.hilt")
                apply("movie.compose")
            }

            dependencies {
                add("implementation", project(":core:common"))
                add("implementation", libs.findLibrary("coroutines").get())
                add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
            }
        }
    }
}
