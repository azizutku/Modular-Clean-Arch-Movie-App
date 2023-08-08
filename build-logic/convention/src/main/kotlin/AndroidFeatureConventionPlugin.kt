import com.azizutku.movie.BuildPlugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply(BuildPlugins.KSP)
                apply(BuildPlugins.KOTLINX_SERIALIZATION)
                apply("movie.android.library")
                apply("movie.android.hilt")
                apply(BuildPlugins.NAVIGATION_SAFEARGS)
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", project(":core:common"))
                add("implementation", project(":core:database"))
                add("implementation", project(":core:ui"))
                add("implementation", project(":core:network"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":core:model"))
                add("implementation", libs.findLibrary("coroutines").get())
                add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
                add("implementation", libs.findLibrary("retrofit").get())
                add("implementation", libs.findLibrary("timber").get())
                add("implementation", libs.findLibrary("material").get())
                add("implementation", libs.findLibrary("glide").get())
                add("implementation", libs.findLibrary("androidx.fragment.ktx").get())
                add("implementation", libs.findLibrary("androidx.core.ktx").get())
                add("implementation", libs.findLibrary("androidx.viewmodel.ktx").get())
                add("implementation", libs.findBundle("androidx.navigation").get())
                add("implementation", libs.findBundle("androidx.lifecycle").get())
                add("implementation", libs.findBundle("androidx.room").get())
                add("ksp", libs.findLibrary("room.compiler").get())
            }
        }
    }
}
