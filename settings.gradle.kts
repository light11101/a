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
include(":labs:food-ordering")
include(":labs:announcements")
include(":labs:product-store")
include(":labs:contact-directory")
include(":labs:background-sync")
include(":labs:reading-list")
include(":labs:comments-api")
include(":labs:feedback-collector")
include(":labs:tip-calculator")
include(":labs:shopping-cart")
include(":labs:firebase-auth")
include(":labs:event-registration")