rootProject.name = "greedy-goblin-libs"

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
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

include(":lib-logging-common")
include(":lib-logging-kermit")
include(":lib-logging-logback")

include(":lib-cor-pattern")
