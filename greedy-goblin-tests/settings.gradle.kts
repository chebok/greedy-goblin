rootProject.name = "greedy-goblin-tests"

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

// plugins {
//    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
// }

// include(":rest-api-v1")
include(":e2e-be")
