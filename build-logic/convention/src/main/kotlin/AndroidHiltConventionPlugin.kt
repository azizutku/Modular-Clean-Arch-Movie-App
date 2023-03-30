import com.azizutku.movie.BuildPlugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(BuildPlugins.HILT_PLUGIN)
                apply(BuildPlugins.KOTLIN_KAPT)
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                "implementation"(libs.findLibrary("hilt").get())
                "kapt"(libs.findLibrary("hilt.compiler").get())
                "kaptAndroidTest"(libs.findLibrary("hilt.compiler").get())
                "kaptTest"(libs.findLibrary("hilt.compiler").get())
            }
            kapt {
                correctErrorTypes = true
            }
        }
    }
}

fun Project.kapt(block: KaptExtension.() -> Unit): Unit =
    (this as ExtensionAware).extensions.configure("kapt", block)
