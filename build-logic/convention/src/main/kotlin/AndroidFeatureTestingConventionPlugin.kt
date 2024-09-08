import com.azizutku.movie.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidFeatureTestingConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("movie.android.library")
                apply("movie.android.hilt")
            }

            dependencies {
                add("implementation", project(":core:common"))
                add("implementation", project(":core:database"))
                add("implementation", project(":core:network"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":core:model"))
                add("api", libs.findLibrary("junit.test").get())
                add("api", libs.findLibrary("hilt.test").get())
                add("api", libs.findLibrary("room.test").get())
                add("api", libs.findLibrary("paging.common.test").get())
                add("api", libs.findLibrary("paging.test").get())
                add("api", libs.findLibrary("turbine.test").get())
                add("api", libs.findLibrary("coroutines.test").get())
                add("api", libs.findLibrary("arch.core.test").get())
                add("api", libs.findLibrary("mockkAndroid.test").get())
                add("api", libs.findLibrary("mockkAgent.test").get())
                add("api", libs.findLibrary("roboelectric.test").get())
                add("api", libs.findLibrary("junit.androidTest").get())
                add("api", libs.findLibrary("espesso.androidTest").get())
                add("api", libs.findLibrary("hilt.androidTest").get())
            }
        }
    }
}
