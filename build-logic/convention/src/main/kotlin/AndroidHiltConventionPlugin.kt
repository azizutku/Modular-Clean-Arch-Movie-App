import com.azizutku.movie.BuildPlugins
import com.azizutku.movie.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(BuildPlugins.HILT_PLUGIN)
                apply(BuildPlugins.KSP)
            }

            dependencies {
                "implementation"(libs.findLibrary("hilt").get())
                "ksp"(libs.findLibrary("hilt.compiler").get())
                "kspAndroidTest"(libs.findLibrary("hilt.compiler").get())
                "kspTest"(libs.findLibrary("hilt.compiler").get())
            }
        }
    }
}
