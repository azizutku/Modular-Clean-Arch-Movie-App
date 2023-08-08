package com.azizutku.movie.extensions

import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register
import java.util.Locale

internal fun Project.configureGitHooks() {
    tasks {
        register<Copy>("copyGitHooks") {
            description = "Copies the git hooks from scripts/git-hooks to the .git folder."
            group = "git hooks"
            from("$rootDir/scripts/git-hooks/") {
                include("**/*.sh")
                rename("(.*).sh", "$1")
            }
            into("$rootDir/.git/hooks")
        }

        register<Exec>("installGitHooks") {
            description = "Installs the pre-commit git hooks from scripts/git-hooks."
            group = "git hooks"
            workingDir(rootDir)
            commandLine("chmod")
            args("-R", "+x", ".git/hooks/")
            dependsOn(named("copyGitHooks"))
            onlyIf {
                isLinuxOrMacOs()
            }
            doLast {
                logger.info("Git hooks installed successfully.")
            }
        }

        register<Delete>("deleteGitHooks") {
            description = "Delete the pre-commit git hooks."
            group = "git hooks"
            delete(fileTree(".git/hooks/"))
        }

        afterEvaluate {
            tasks["clean"].dependsOn(tasks.named("installGitHooks"))
        }
    }
}

internal fun isLinuxOrMacOs(): Boolean {
    val osName = System.getProperty("os.name").lowercase(Locale.ROOT)
    return listOf("linux", "mac os", "macos").contains(osName)
}
