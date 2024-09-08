import com.azizutku.movie.BuildPlugins
import org.gradle.api.Plugin
import org.gradle.api.Project

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
        }
    }
}
