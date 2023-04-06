package com.azizutku.movie.extensions

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

internal fun Project.configureDetekt() {
    configure<DetektExtension> {
        source = project.files("src/main/kotlin")
        buildUponDefaultConfig = true
        allRules = false
        config = files("$rootDir/.detekt/config.yml")
        baseline = file("$rootDir/.detekt/baseline.xml")
    }
    tasks.withType<Detekt>().configureEach {
        reports {
            html.required.set(true)
            html.outputLocation.set(file("build/reports/detekt/report.html"))
            xml.required.set(true)
            xml.outputLocation.set(file("build/reports/detekt/report.xml"))
            txt.required.set(true)
            txt.outputLocation.set(file("build/reports/detekt/report.txt"))
        }
    }
    tasks.withType<Detekt>().configureEach {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    tasks.withType<DetektCreateBaselineTask>().configureEach {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}
