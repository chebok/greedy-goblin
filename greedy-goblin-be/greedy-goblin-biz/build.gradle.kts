plugins {
    id("build-jvm")
}

dependencies {
    implementation(project(":greedy-goblin-common"))
    implementation(project(":greedy-goblin-stubs"))
    testImplementation(kotlin("test-junit"))
}
