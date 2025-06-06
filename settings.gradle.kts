pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        // Kotlin and Android
        id("org.jetbrains.kotlin.android")          version "1.9.0"   apply false
        id("com.android.application")               version "8.5.1"   apply false

        // Dagger-Hilt Gradle plugin
        id("com.google.dagger.hilt.android") version "2.48" apply false


        // KSP Gradle plugin (must match Kotlin version)
        id("com.google.devtools.ksp")               version "1.9.0-1.0.13"  apply false
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "money"
include(":app")
