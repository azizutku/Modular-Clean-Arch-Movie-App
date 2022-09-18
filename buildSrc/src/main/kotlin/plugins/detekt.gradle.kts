package plugins

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

apply<DetektPlugin>()

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
    jvmTarget = "1.8"
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}
