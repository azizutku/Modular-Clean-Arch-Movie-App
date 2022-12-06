import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.ProductFlavor
import com.android.build.api.dsl.TestProductFlavor
import org.gradle.api.NamedDomainObjectContainer

interface BuildProductFlavor<T: ProductFlavor> {
    val name: String

    fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<T>,
        isApp: Boolean = false,
    ): T

    companion object {
        const val DEVELOPMENT = "dev"
        const val TEST = "qa"
        const val PRODUCTION = "prod"
    }
}

object ProductFlavorDev : BuildProductFlavor<ApplicationProductFlavor> {

    override val name = BuildProductFlavor.DEVELOPMENT

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

object ProductFlavorQA : BuildProductFlavor<ApplicationProductFlavor> {
    override val name = BuildProductFlavor.TEST

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

object ProductFlavorProduction : BuildProductFlavor<ApplicationProductFlavor> {
    override val name = BuildProductFlavor.PRODUCTION

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>,
        isApp: Boolean,
    ): ApplicationProductFlavor {
        return namedDomainObjectContainer.create(name) {
            dimension = BuildProductDimensions.DIMENSION_VERSION
        }
    }
}

object TestProductFlavorDev : BuildProductFlavor<TestProductFlavor> {

    override val name = BuildProductFlavor.DEVELOPMENT

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<TestProductFlavor>,
        isApp: Boolean,
    ): TestProductFlavor {
        return namedDomainObjectContainer.create(name) {
            dimension = BuildProductDimensions.DIMENSION_VERSION
        }
    }
}

object TestProductFlavorQA : BuildProductFlavor<TestProductFlavor> {

    override val name = BuildProductFlavor.TEST

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<TestProductFlavor>,
        isApp: Boolean,
    ): TestProductFlavor {
        return namedDomainObjectContainer.create(name) {
            dimension = BuildProductDimensions.DIMENSION_VERSION
        }
    }
}

object TestProductFlavorProduction : BuildProductFlavor<TestProductFlavor> {

    override val name = BuildProductFlavor.PRODUCTION

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<TestProductFlavor>,
        isApp: Boolean,
    ): TestProductFlavor {
        return namedDomainObjectContainer.create(name) {
            dimension = BuildProductDimensions.DIMENSION_VERSION
        }
    }
}
