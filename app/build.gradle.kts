import extensions.buildConfigStringField
import extensions.getLocalProperty

plugins {
    id(BuildPlugins.ANDROID_APPLICATION)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.HILT_PLUGIN)
    id(BuildPlugins.NAVIGATION_SAFEARGS)
    id(BuildPlugins.KOTLINX_SERIALIZATION)
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
    buildTypes.forEach {
        kotlin.runCatching {
            it.buildConfigStringField(BuildConstants.CONFIG_NAME_API_BASE_URL, BuildConstants.CONFIG_VALUE_API_BASE_URL)
            it.buildConfigStringField(
                BuildConstants.CONFIG_NAME_API_KEY,
                getLocalProperty(BuildConstants.PROPERTY_NAME_API_KEY),
            )
        }.onFailure {
            throw InvalidUserDataException(BuildConstants.MESSAGE_API_KEY_EXCEPTION)
        }
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
        freeCompilerArgs = freeCompilerArgs
            .plus("-Xopt-in=kotlin.RequiresOptIn")
            .plus("-Xcontext-receivers")
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.splash.screen)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.viewmodel.ktx)
    implementation(libs.androidx.paging)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.bundles.androidx.room)
    implementation(libs.material)
    implementation(libs.hilt)
    implementation(libs.coroutines)
    implementation(libs.timber)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.lottie)
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.kotlinx.serialization.converter)
    implementation(libs.glide)

    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)

    testImplementation(libs.junit.test)
    testImplementation(libs.hilt.test)
    testImplementation(libs.room.test)
    testImplementation(libs.paging.test)

    androidTestImplementation(libs.junit.androidTest)
    androidTestImplementation(libs.espesso.androidTest)
    androidTestImplementation(libs.hilt.androidTest)
    androidTestImplementation(libs.navigation.androidTest)

    kapt(libs.hilt.kapt)
    kapt(libs.room.kapt)
    kaptAndroidTest(libs.hilt.kapt)
    kaptTest(libs.hilt.kapt)
}

kapt {
    correctErrorTypes = true
}
