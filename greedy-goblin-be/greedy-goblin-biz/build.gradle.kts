plugins {
    id("build-jvm")
}

dependencies {
    implementation(project(":greedy-goblin-common"))
    implementation(libs.cor)
    implementation(libs.konform)
    implementation(libs.coroutines.core)
    testImplementation(kotlin("test-junit"))
    testImplementation(libs.coroutines.test) // api?
}
