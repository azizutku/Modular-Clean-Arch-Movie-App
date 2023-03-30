import com.azizutku.movie.extensions.configureGitHooks
import org.gradle.api.Plugin
import org.gradle.api.Project

class GitHooksConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configureGitHooks()
    }
}
