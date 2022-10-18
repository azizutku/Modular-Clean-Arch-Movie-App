package extensions

import org.gradle.api.Project
import utils.getLocalProperty

fun Project.getLocalProperty(propertyName: String): String {
    return getLocalProperty(propertyName, this)
}
