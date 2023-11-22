pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // maven("/Users/ashikov/aosp/out/androidx/build/support_repo/")
    }
}

rootProject.name = "ComposeNumbers"
include(":benchmark")
