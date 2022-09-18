import com.android.build.api.dsl.ApplicationProductFlavor
import org.gradle.api.NamedDomainObjectContainer

interface BuildProductFlavor {
    val name: String

    fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>,
        isApp: Boolean = false,
    ): ApplicationProductFlavor
}

object ProductFlavorDev : BuildProductFlavor {

    override val name = "dev"

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>,
        isApp: Boolean,
    ): ApplicationProductFlavor {
        return namedDomainObjectContainer.create(name) {
            if (isApp) {
                applicationIdSuffix = ".dev"
            }
            versionNameSuffix = "-dev"
            dimension = BuildProductDimensions.DIMENSION_VERSION
        }
    }
}

object ProductFlavorQA : BuildProductFlavor {
    override val name = "qa"

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>,
        isApp: Boolean,
    ): ApplicationProductFlavor {
        return namedDomainObjectContainer.create(name) {
            if (isApp) {
                applicationIdSuffix = ".qa"
            }
            versionNameSuffix = "-qa"
            dimension = BuildProductDimensions.DIMENSION_VERSION
        }
    }
}

object ProductFlavorProduction : BuildProductFlavor {
    override val name = "prod"

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>,
        isApp: Boolean,
    ): ApplicationProductFlavor {
        return namedDomainObjectContainer.create(name) {
            dimension = BuildProductDimensions.DIMENSION_VERSION
        }
    }
}
