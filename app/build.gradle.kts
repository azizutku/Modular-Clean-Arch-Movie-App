import dependencies.Dependencies
import dependencies.TestAndroidDependencies
import dependencies.TestDependencies

plugins {
    id(BuildPlugins.ANDROID_APPLICATION)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.KOTLIN_ANDROID)
}

android {
    compileSdk = AndroidConfig.COMPILE_SDK

    defaultConfig {
        applicationId = AndroidConfig.APP_ID
        minSdk = AndroidConfig.MIN_SDK
        targetSdk = AndroidConfig.TARGET_SDK
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME
        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }
    buildTypes {
        BuildTypeDebug.create(this)
        BuildTypeRelease.create(this)
    }
    flavorDimensions.add(BuildProductDimensions.DIMENSION_VERSION)
    productFlavors {
        ProductFlavorDev.create(this, true)
        ProductFlavorQA.create(this, true)
        ProductFlavorProduction.create(this, true)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        viewBinding = true
    }
    packagingOptions {
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
    namespace = "com.azizutku.movie"
}

dependencies {
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.APP_COMPAT)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.CONSTRAINT_LAYOUT)

    testImplementation(TestDependencies.JUNIT)

    androidTestImplementation(TestAndroidDependencies.JUNIT)
    androidTestImplementation(TestAndroidDependencies.ESPRESSO)
}
