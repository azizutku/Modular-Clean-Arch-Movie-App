import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.azizutku.movie.BuildPlugins
import com.azizutku.movie.extensions.configureJacoco
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryJacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(BuildPlugins.ANDROID_LIBRARY)
                apply(BuildPlugins.GRADLE_JACOCO)
            }
            val extension = extensions.getByType<LibraryAndroidComponentsExtension>()
            configureJacoco(extension)
        }
    }

}
