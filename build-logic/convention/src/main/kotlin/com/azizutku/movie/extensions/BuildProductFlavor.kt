package com.azizutku.movie.extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

enum class FlavorDimension(val value: String) {
    VERSION("version"),
}

enum class BuildProductFlavor(
    val flavourName: String,
    val dimension: FlavorDimension,
    val applicationIdSuffix: String? = null,
    val versionNameSuffix: String? = null,
) {
    DEVELOPMENT("dev", FlavorDimension.VERSION, ".dev", "-dev"),
    TEST("qa", FlavorDimension.VERSION, ".qa", "-qa"),
    PRODUCTION("prod", FlavorDimension.VERSION, ".prod", "-prod"),
}

fun configureFlavors(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {
        flavorDimensions += FlavorDimension.values().map { dimension ->
            dimension.value
        }
        productFlavors {
            BuildProductFlavor.values().forEach { productFlavour ->
                create(productFlavour.flavourName) {
                    dimension = productFlavour.dimension.value
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        productFlavour.versionNameSuffix?.let { versionNameSuffix ->
                            this.versionNameSuffix = versionNameSuffix
                        }
                        productFlavour.applicationIdSuffix?.let { applicationIdSuffix ->
                            this.applicationIdSuffix = applicationIdSuffix
                        }
                    }
                }
            }
        }
    }
}
