import com.azizutku.movie.BuildPlugins
import com.azizutku.movie.extensions.configureAndroidCompose
import com.azizutku.movie.extensions.configureDetekt
import com.azizutku.movie.extensions.getCommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(BuildPlugins.COMPOSE)
            }
            configureAndroidCompose(getCommonExtension())
            configureDetekt(true)
        }
    }
}
