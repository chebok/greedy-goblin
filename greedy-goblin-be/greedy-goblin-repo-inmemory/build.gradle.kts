plugins {
    id("build-jvm")
}

group = "io.greedy.goblin.repo"
version = "0.0.1"

dependencies {
    implementation(projects.greedyGoblinCommon)

    implementation(libs.coroutines.core)
    implementation(libs.db.cache4k)
    testImplementation(kotlin("test-junit"))
}
