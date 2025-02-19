plugins {
    id("build-jvm")
    alias(libs.plugins.kotlinx.serialization)
}

group = "io.greedy.goblin.repo"
version = "0.0.1"

dependencies {
    implementation(projects.greedyGoblinCommon)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coroutines.core)
    implementation(libs.bundles.exposed)
    implementation(libs.db.hikari)
    testImplementation(kotlin("test-junit"))
}
