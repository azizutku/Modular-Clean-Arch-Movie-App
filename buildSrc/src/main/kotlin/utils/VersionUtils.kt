package utils

object VersionUtils {

    private val stableKeywords = listOf("RELEASE", "FINAL", "GA")

    fun isNonStable(version: String): Boolean {
        val hasStableKeyword = stableKeywords.any { version.toUpperCase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = hasStableKeyword || regex.matches(version)
        return isStable.not()
    }
}
