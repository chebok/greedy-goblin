plugins {
    id("build-jvm")
}

group = "io.goblingamble"
version = "0.0.1"

dependencies {
    api(libs.kotlinx.datetime)
    testImplementation(kotlin("test-junit"))
}
