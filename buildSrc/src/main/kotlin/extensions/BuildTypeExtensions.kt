package extensions

import com.android.build.gradle.internal.dsl.BuildType

fun BuildType.buildConfigStringField(name: String, value: String) {
    this.buildConfigField("String", name, "\"$value\"")
}
