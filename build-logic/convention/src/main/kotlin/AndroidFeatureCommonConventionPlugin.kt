import com.azizutku.movie.BuildPlugins
import com.azizutku.movie.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureCommonConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply(BuildPlugins.KSP)
                apply(BuildPlugins.KOTLINX_SERIALIZATION)
                apply("movie.android.library")
                apply("movie.android.hilt")
                apply(BuildPlugins.NAVIGATION_SAFEARGS)
            }
            dependencies {
                add("implementation", project(":core:common"))
                add("implementation", project(":core:database"))
                add("implementation", project(":core:network"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":core:model"))
                add("implementation", libs.findLibrary("coroutines").get())
                add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
                add("implementation", libs.findLibrary("retrofit").get())
                add("implementation", libs.findLibrary("timber").get())
                add("implementation", libs.findBundle("androidx.lifecycle").get())
                add("implementation", libs.findBundle("androidx.room").get())
                add("ksp", libs.findLibrary("room.compiler").get())
            }
        }
    }
}
