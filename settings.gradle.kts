pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        gradlePluginPortal()

        jcenter()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        maven(url = "https://jitpack.io")
        mavenCentral()

        jcenter()
    }
}

rootProject.name = "AsaxiyBooks"
include(":app")
 