package com.azizutku.movie

import com.android.build.api.dsl.ApkSigningConfig
import org.gradle.api.NamedDomainObjectContainer
import java.io.File

interface BuildSigningConfig {

    fun create(file: File, namedDomainObjectContainer: NamedDomainObjectContainer<out ApkSigningConfig>)
}

object BuildDebugSigningConfig : BuildSigningConfig {

    override fun create(file: File, namedDomainObjectContainer: NamedDomainObjectContainer<out ApkSigningConfig>) {
        namedDomainObjectContainer.getByName(BuildType.DEBUG) {
            storeFile = file
            storePassword = "cleanmovieapp"
            keyAlias = "cleanmovieapp"
            keyPassword = "cleanmovieapp"
        }
    }
}

object BuildReleaseSigningConfig : BuildSigningConfig {

    override fun create(file: File, namedDomainObjectContainer: NamedDomainObjectContainer<out ApkSigningConfig>) {
        namedDomainObjectContainer.create(BuildType.RELEASE) {
            storeFile = file
            storePassword = "cleanmovieapp"
            keyAlias = "cleanmovieapp"
            keyPassword = "cleanmovieapp"
        }
    }
}
