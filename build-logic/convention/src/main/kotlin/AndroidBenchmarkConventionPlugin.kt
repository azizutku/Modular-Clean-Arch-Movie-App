import com.android.build.api.dsl.TestExtension
import com.android.build.api.variant.TestAndroidComponentsExtension
import com.azizutku.movie.AndroidConfig
import com.azizutku.movie.BuildPlugins
import com.azizutku.movie.BuildProductDimensions
import com.azizutku.movie.BuildType
import com.azizutku.movie.TestBuildTypeBenchmark
import com.azizutku.movie.extensions.BuildProductFlavor
import com.azizutku.movie.extensions.configureFlavors
import com.azizutku.movie.extensions.configureKotlinAndroid
import com.azizutku.movie.utils.configureGradleManagedDevices
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidBenchmarkConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(BuildPlugins.ANDROID_TEST)
                apply(BuildPlugins.KOTLIN_ANDROID)
            }
            extensions.configure<TestExtension> {
                configureKotlinAndroid(this, false)
                defaultConfig.targetSdk = AndroidConfig.TARGET_SDK
                defaultConfig.testInstrumentationRunner = AndroidConfig.TEST_BENCHMARK_INSTRUMENTATION_RUNNER
                defaultConfig.missingDimensionStrategy(
                    BuildProductDimensions.DIMENSION_VERSION,
                    BuildProductFlavor.PRODUCTION.flavourName
                )
                configureFlavors(this)
                buildTypes {
                    // This benchmark buildType is used for benchmarking, and should function like your
                    // release build (for example, with minification on). It's signed with a debug key
                    // for easy local/CI testing.
                    TestBuildTypeBenchmark.create(this, signingConfigs)
                }
                targetProjectPath = AndroidConfig.BENCHMARK_TARGET_PROJECT_PATH
                experimentalProperties["android.experimental.self-instrumenting"] = true
                configureGradleManagedDevices(this)
                buildFeatures.buildConfig = true
                namespace = "com.azizutku.movie.benchmark"
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                "implementation"(libs.findLibrary("junit.androidTest").get())
                "implementation"(libs.findLibrary("espesso.androidTest").get())
                "implementation"(libs.findLibrary("uiautomator.androidTest").get())
                "implementation"(libs.findLibrary("benchmark.macro.androidTest").get())
                "detektPlugins"(libs.findBundle("detekt").get())
            }
            androidComponents {
                beforeVariants(selector().all()) {
                    it.enable = it.buildType == BuildType.BENCHMARK
                }
            }
        }
    }
}

internal fun Project.androidComponents(configure: Action<TestAndroidComponentsExtension>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("androidComponents", configure)

