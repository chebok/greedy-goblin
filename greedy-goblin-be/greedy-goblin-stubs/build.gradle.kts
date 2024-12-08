plugins {
    id("build-jvm")
}

dependencies {
    implementation(project(":greedy-goblin-common"))
    testImplementation(kotlin("test-junit"))
}
