import com.azizutku.movie.BuildPlugins
import com.azizutku.movie.extensions.libs
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.process.CommandLineArgumentProvider
import java.io.File

class AndroidRoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(BuildPlugins.KSP)

            extensions.configure<KspExtension> {
                arg(RoomSchemaArgProvider(File(projectDir, "schemas")))
            }
            
            dependencies {
                add("implementation", libs.findBundle("androidx.room").get())
                add("ksp", libs.findLibrary("room.compiler").get())
            }
        }
    }

    class RoomSchemaArgProvider(
        @get:InputDirectory
        @get:PathSensitive(PathSensitivity.RELATIVE)
        val schemaDir: File,
    ) : CommandLineArgumentProvider {
        override fun asArguments() = listOf("room.schemaLocation=${schemaDir.path}")
    }
}
