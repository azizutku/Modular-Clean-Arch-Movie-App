import com.azizutku.movie.extensions.configureDetekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(DetektPlugin::class.java)
            configureDetekt()
        }
    }
}
