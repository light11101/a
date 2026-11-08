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
    }
}

rootProject.name = "My Application"
include(":app")
include(":labs:layouts")
include(":labs:food-ordering")
include(":labs:announcements")
include(":labs:product-store")
include(":labs:contact-directory")
include(":labs:reading-list")
include(":labs:comments-api")
include(":labs:background-sync")
include(":labs:fragments")