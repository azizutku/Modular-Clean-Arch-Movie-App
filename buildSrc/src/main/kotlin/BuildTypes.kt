import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.api.dsl.TestBuildType
import com.android.build.gradle.internal.dsl.SigningConfig
import org.gradle.api.NamedDomainObjectContainer

interface BuildType<T: com.android.build.api.dsl.BuildType> {
    val name: String

    fun create(
        buildTypeNamedDomainObjectContainer: NamedDomainObjectContainer<T>,
        signingConfigDomainObjectContainer: NamedDomainObjectContainer<SigningConfig>? = null,
    ): T

    companion object {
        const val DEBUG = "debug"
        const val RELEASE = "release"
        const val BENCHMARK = "benchmark"
    }
}

object BuildTypeDebug : BuildType<ApplicationBuildType> {
    override val name = BuildType.DEBUG

    override fun create(
        buildTypeNamedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>,
        signingConfigDomainObjectContainer: NamedDomainObjectContainer<SigningConfig>?,
    ): ApplicationBuildType {
        return buildTypeNamedDomainObjectContainer.getByName(name) {
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }
}

object BuildTypeRelease : BuildType<ApplicationBuildType> {
    override val name = BuildType.RELEASE

    override fun create(
        buildTypeNamedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>,
        signingConfigDomainObjectContainer: NamedDomainObjectContainer<SigningConfig>?,
    ): ApplicationBuildType {
        return buildTypeNamedDomainObjectContainer.getByName(name) {
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }
}

object BuildTypeBenchmark : BuildType<ApplicationBuildType> {
    override val name = BuildType.BENCHMARK

    override fun create(
        buildTypeNamedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>,
        signingConfigDomainObjectContainer: NamedDomainObjectContainer<SigningConfig>?,
    ): ApplicationBuildType {
        return buildTypeNamedDomainObjectContainer.create(name) {
            initWith(buildTypeNamedDomainObjectContainer.getByName(BuildType.RELEASE))
            signingConfig = buildTypeNamedDomainObjectContainer.getByName(BuildType.DEBUG).signingConfig
            matchingFallbacks.add(BuildType.RELEASE)
            proguardFiles("benchmark-rules.pro")
        }
    }
}

object TestBuildTypeBenchmark : BuildType<TestBuildType> {
    override val name = BuildType.BENCHMARK

    override fun create(
        buildTypeNamedDomainObjectContainer: NamedDomainObjectContainer<TestBuildType>,
        signingConfigDomainObjectContainer: NamedDomainObjectContainer<SigningConfig>?,
    ): TestBuildType {
        return buildTypeNamedDomainObjectContainer.create(name) {
            isDebuggable = true
            signingConfig = buildTypeNamedDomainObjectContainer.getByName(BuildType.DEBUG).signingConfig
            matchingFallbacks += listOf(BuildType.RELEASE)
        }
    }
}
