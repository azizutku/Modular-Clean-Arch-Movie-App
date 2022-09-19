package dependencies

object TestAndroidDependencies {

    const val JUNIT = "androidx.test.ext:junit:${BuildDependenciesVersions.ANDROID_JUNIT}"
    const val ESPRESSO =
        "androidx.test.espresso:espresso-core:${BuildDependenciesVersions.ESPRESSO}"
    const val HILT =
        "com.google.dagger:hilt-android-testing:${BuildDependenciesVersions.HILT}"
    const val NAVIGATION =
        "androidx.navigation:navigation-testing:${BuildDependenciesVersions.NAVIGATION}"

}
