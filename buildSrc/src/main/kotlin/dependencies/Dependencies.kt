package dependencies

object Dependencies {

    // Androidx
    const val CORE_KTX = "androidx.core:core-ktx:${BuildDependenciesVersions.CORE_KTX}"
    const val APP_COMPAT = "androidx.appcompat:appcompat:${BuildDependenciesVersions.APP_COMPAT}"
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${BuildDependenciesVersions.CONSTRAINT_LAYOUT}"
    const val SPLASH_SCREEN =
        "androidx.core:core-splashscreen:${BuildDependenciesVersions.SPLASH_SCREEN}"
    const val FRAGMENT_KTX =
        "androidx.fragment:fragment-ktx:${BuildDependenciesVersions.FRAGMENT_KTX}"
    const val VIEW_MODEL_KTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${BuildDependenciesVersions.VIEW_MODEL_KTX}"
    const val NAVIGATION_FRAGMENT =
        "androidx.navigation:navigation-fragment-ktx:${BuildDependenciesVersions.NAVIGATION}"
    const val NAVIGATION_UI =
        "androidx.navigation:navigation-ui-ktx:${BuildDependenciesVersions.NAVIGATION}"
    const val LIFECYCLE =
        "androidx.lifecycle:lifecycle-runtime-ktx:${BuildDependenciesVersions.LIFECYCLE}"
    const val LIFECYCLE_VIEWMODEL =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${BuildDependenciesVersions.LIFECYCLE}"
    const val LIFECYCLE_VIEWMODEL_SAVED_STATE =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${BuildDependenciesVersions.LIFECYCLE}"
    const val ROOM = "androidx.room:room-runtime:${BuildDependenciesVersions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${BuildDependenciesVersions.ROOM}"
    const val ROOM_PAGING = "androidx.room:room-paging:${BuildDependenciesVersions.ROOM}"
    const val PAGING = "androidx.paging:paging-runtime:${BuildDependenciesVersions.PAGING}"
    const val SWIPE_REFRESH_LAYOUT =
        "androidx.swiperefreshlayout:swiperefreshlayout:${BuildDependenciesVersions.SWIPE_REFRESH_LAYOUT}"

    // Google
    const val MATERIAL =
        "com.google.android.material:material:${BuildDependenciesVersions.MATERIAL}"

    // Dependency injection
    const val HILT = "com.google.dagger:hilt-android:${BuildDependenciesVersions.HILT}"

    // Logging
    const val TIMBER = "com.jakewharton.timber:timber:${BuildDependenciesVersions.TIMBER}"

    // Kotlin
    const val COROUTINES =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${BuildDependenciesVersions.COROUTINES}"
    const val KOTLINX_SERIALIZATION_JSON = "org.jetbrains.kotlinx:kotlinx-serialization-json:${
        BuildDependenciesVersions
            .KOTLINX_SERIALIZATION_JSON
    }"

    // Animation
    const val LOTTIE = "com.airbnb.android:lottie:${BuildDependenciesVersions.LOTTIE}"

    // Network
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${BuildDependenciesVersions.RETROFIT}"
    const val LOGGING_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${BuildDependenciesVersions.LOGGING_INTERCEPTOR}"
    const val KOTLINX_SERIALIZATION_CONVERTER =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:" +
            BuildDependenciesVersions.KOTLINX_SERIALIZATION_CONVERTER_VERSION
    const val CHUCKER = "com.github.chuckerteam.chucker:library:${BuildDependenciesVersions.CHUCKER}"
    const val CHUCKER_NO_OP = "com.github.chuckerteam.chucker:library-no-op:${BuildDependenciesVersions.CHUCKER}"

    // Image loading
    const val GLIDE = "com.github.bumptech.glide:glide:${BuildDependenciesVersions.GLIDE}"
}
