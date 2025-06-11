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
        id("org.jetbrains.kotlin.android")          version "1.9.0"   apply false
        id("com.android.application")               version "8.10.1" apply false
        id("com.google.dagger.hilt.android") version "2.48" apply false
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
