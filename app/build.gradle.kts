import dependencies.AnnotationProcessorsDependencies
import dependencies.Dependencies
import dependencies.TestAndroidDependencies
import dependencies.TestDependencies
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
    implementation(Dependencies.SPLASH_SCREEN)
    implementation(Dependencies.HILT)
    implementation(Dependencies.COROUTINES)
    implementation(Dependencies.TIMBER)
    implementation(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.VIEW_MODEL_KTX)
    implementation(Dependencies.NAVIGATION_FRAGMENT)
    implementation(Dependencies.NAVIGATION_UI)
    implementation(Dependencies.KOTLINX_SERIALIZATION_JSON)
    implementation(Dependencies.LOTTIE)
    implementation(Dependencies.LIFECYCLE)
    implementation(Dependencies.LIFECYCLE_VIEWMODEL)
    implementation(Dependencies.LIFECYCLE_VIEWMODEL_SAVED_STATE)
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.LOGGING_INTERCEPTOR)
    implementation(Dependencies.KOTLINX_SERIALIZATION_CONVERTER)
    implementation(Dependencies.ROOM)
    implementation(Dependencies.ROOM_KTX)
    implementation(Dependencies.ROOM_PAGING)
    implementation(Dependencies.PAGING)
    implementation(Dependencies.GLIDE)
    implementation(Dependencies.SWIPE_REFRESH_LAYOUT)

    debugImplementation(Dependencies.CHUCKER)
    releaseImplementation(Dependencies.CHUCKER_NO_OP)

    testImplementation(TestDependencies.JUNIT)
    testImplementation(TestDependencies.HILT)
    testImplementation(TestDependencies.ROOM)
    testImplementation(TestDependencies.PAGING)

    androidTestImplementation(TestAndroidDependencies.JUNIT)
    androidTestImplementation(TestAndroidDependencies.ESPRESSO)
    androidTestImplementation(TestAndroidDependencies.HILT)
    androidTestImplementation(TestAndroidDependencies.NAVIGATION)

    kapt(AnnotationProcessorsDependencies.HILT)
    kapt(AnnotationProcessorsDependencies.ROOM)
    kaptAndroidTest(AnnotationProcessorsDependencies.HILT)
    kaptTest(AnnotationProcessorsDependencies.HILT)
}

kapt {
    correctErrorTypes = true
}
