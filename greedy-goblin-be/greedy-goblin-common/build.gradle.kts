plugins {
    id("build-jvm")
}

group = "io.greedy.goblin.common"
version = "0.0.1"

dependencies {
    api(libs.kotlinx.datetime)
    api("io.greedy.goblin.libs:lib-logging-common")
    testImplementation(kotlin("test-junit"))
}
