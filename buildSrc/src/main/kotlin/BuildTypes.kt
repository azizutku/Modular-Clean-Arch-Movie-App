import com.android.build.api.dsl.ApplicationBuildType
import org.gradle.api.NamedDomainObjectContainer

interface BuildType {
    val name: String

    fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>,
    ): ApplicationBuildType

    companion object {
        const val DEBUG = "debug"
        const val RELEASE = "release"
    }
}

object BuildTypeDebug : BuildType {
    override val name = BuildType.DEBUG

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>
    ): ApplicationBuildType {
        return namedDomainObjectContainer.getByName(name) {
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }
}

object BuildTypeRelease : BuildType {
    override val name = BuildType.RELEASE

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>
    ): ApplicationBuildType {
        return namedDomainObjectContainer.getByName(name) {
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }
}
