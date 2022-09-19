package dependencies

object TestDependencies {

    const val JUNIT = "junit:junit:${BuildDependenciesVersions.JUNIT}"
    const val HILT =
        "com.google.dagger:hilt-android-testing:${BuildDependenciesVersions.HILT}"
    const val ROOM = "androidx.room:room-testing:${BuildDependenciesVersions.ROOM}"
    const val PAGING = "androidx.paging:paging-common:${BuildDependenciesVersions.PAGING}"
}
