rootProject.name = "greedy-goblin-be"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/version-catalogs/libs.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("../build-plugins")
    plugins {
        id("build-jvm") apply false
        id("build-kmp") apply false
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":rest-api-v1")
include(":greedy-goblin-common")
include(":greedy-goblin-log")
include(":greedy-goblin-biz")
include(":greedy-goblin-api")
// DB
include(":greedy-goblin-repo-inmemory")
