import com.azizutku.movie.BuildPlugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(BuildPlugins.HILT_PLUGIN)
                apply(BuildPlugins.KSP)
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                "implementation"(libs.findLibrary("hilt").get())
                "ksp"(libs.findLibrary("hilt.compiler").get())
                "kspAndroidTest"(libs.findLibrary("hilt.compiler").get())
                "kspTest"(libs.findLibrary("hilt.compiler").get())
            }
        }
    }
}
