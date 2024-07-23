pluginManagement {
    repositories {
        maven(url ="https://maven.aliyun.com/repository/google")
        maven(url ="https://maven.aliyun.com/repository/central")
        maven(url ="https://maven.aliyun.com/repository/gradle-plugin")
        maven { url = uri("https://jitpack.io") }
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
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven(url ="https://maven.aliyun.com/repository/google")
        maven(url ="https://maven.aliyun.com/repository/central")
        maven { url = uri("https://jitpack.io") }
        google()
        mavenCentral()
    }
}

rootProject.name = "Test"
include(":app")
 