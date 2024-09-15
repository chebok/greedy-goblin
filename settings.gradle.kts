pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion apply false
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "greedy-goblin"
include("m1l1-first")
include("m1l2-basic")
include("m1l3-func")
include("m1l4-oop")
