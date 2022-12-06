plugins {
    id(BuildPlugins.ANDROID_TEST)
    id(BuildPlugins.KOTLIN_ANDROID)
}

android {
    namespace = "com.azizutku.movie.benchmark"
    compileSdk = AndroidConfig.COMPILE_SDK

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK
        targetSdk = AndroidConfig.TARGET_SDK

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
        missingDimensionStrategy(BuildProductDimensions.DIMENSION_VERSION, BuildProductFlavor.PRODUCTION)
    }
    flavorDimensions.add(BuildProductDimensions.DIMENSION_VERSION)
    productFlavors {
        TestProductFlavorDev.create(this)
        TestProductFlavorQA.create(this)
        TestProductFlavorProduction.create(this)
    }

    buildTypes {
        // This benchmark buildType is used for benchmarking, and should function like your
        // release build (for example, with minification on). It's signed with a debug key
        // for easy local/CI testing.
        TestBuildTypeBenchmark.create(this)
    }

    targetProjectPath = AndroidConfig.BENCHMARK_TARGET_PROJECT_PATH
    experimentalProperties["android.experimental.self-instrumenting"] = true
}

dependencies {
    implementation(libs.junit.androidTest)
    implementation(libs.espesso.androidTest)
    implementation(libs.uiautomator.androidTest)
    implementation(libs.benchmark.macro.androidTest)
}

androidComponents {
    beforeVariants(selector().all()) {
        it.enabled = it.buildType == BuildType.BENCHMARK
    }
}
