import com.android.build.api.dsl.ApplicationExtension
import com.azizutku.movie.AndroidConfig
import com.azizutku.movie.BuildDebugSigningConfig
import com.azizutku.movie.BuildPlugins
import com.azizutku.movie.BuildReleaseSigningConfig
import com.azizutku.movie.BuildTypeBenchmark
import com.azizutku.movie.BuildTypeDebug
import com.azizutku.movie.BuildTypeRelease
import com.azizutku.movie.extensions.JDK_VERSION
import com.azizutku.movie.extensions.configureFlavors
import com.azizutku.movie.extensions.configureKotlinAndroid
import com.azizutku.movie.extensions.kotlin
import com.azizutku.movie.utils.configureGradleManagedDevices
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(BuildPlugins.ANDROID_APPLICATION)
                apply(BuildPlugins.KOTLIN_ANDROID)
                apply(BuildPlugins.NAVIGATION_SAFEARGS)
                apply(BuildPlugins.KOTLINX_SERIALIZATION)
            }
            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                configureFlavors(this)
                defaultConfig.applicationId = AndroidConfig.APP_ID
                defaultConfig.targetSdk = AndroidConfig.TARGET_SDK
                defaultConfig.versionCode = AndroidConfig.VERSION_CODE
                defaultConfig.versionName = AndroidConfig.VERSION_NAME
                signingConfigs {
                    BuildDebugSigningConfig.create(file("../debug.keystore"), this)
                    BuildReleaseSigningConfig.create(file("../release.keystore"), this)
                }
                buildTypes {
                    BuildTypeDebug.create(this)
                    BuildTypeRelease.create(this)
                    BuildTypeBenchmark.create(this, signingConfigs)
                }
                packagingOptions {
                    resources.excludes.add("META-INF/AL2.0")
                    resources.excludes.add("META-INF/LGPL2.1")
                }
                configureGradleManagedDevices(this)
                namespace = AndroidConfig.NAMESPACE
            }
            kotlin {
                jvmToolchain(JDK_VERSION)
            }
        }
    }

}
