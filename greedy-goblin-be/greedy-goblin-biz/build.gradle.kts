plugins {
    id("build-jvm")
}

dependencies {
    implementation(project(":greedy-goblin-common"))
    implementation(libs.cor)
    testImplementation(kotlin("test-junit"))
    testImplementation(libs.coroutines.test) // api?
}
