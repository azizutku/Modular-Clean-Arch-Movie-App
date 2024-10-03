pluginManagement {
    repositories {
        includeBuild("build-logic")
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "dagger.hilt.android.plugin") {
                useModule("com.google.dagger:hilt-android-gradle-plugin:${requested.version}")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Clean Movie App"

include(":app")
include(":app-compose")
include(":benchmark")
include(
    ":core:common",
    ":core:ui:xml",
    ":core:ui:compose",
    ":core:ui:common",
    ":core:network",
    ":core:database",
    ":core:domain",
    ":core:model",
    ":core:testing",
)
include(
    ":feature:trending:common",
    ":feature:trending:presentation-xml",
    ":feature:trending:presentation-compose",
    ":feature:trending:testing",
)
include(
    ":feature:movie:common",
    ":feature:movie:presentation-xml",
    ":feature:movie:presentation-compose",
)
include(
    ":feature:watchlist:common",
    ":feature:watchlist:presentation-xml",
    ":feature:watchlist:presentation-compose",
)
