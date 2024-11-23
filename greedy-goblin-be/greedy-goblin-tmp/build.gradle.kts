plugins {
    id("build-jvm")
    application
}

application {
    mainClass.set("io.greedy.goblin.tmp.MainKt")
}

dependencies {
    implementation(project(":greedy-goblin-log"))
    implementation("io.greedy.goblin.libs:lib-logging-common")
    implementation("io.greedy.goblin.libs:lib-logging-logback")

    implementation(project(":greedy-goblin-common"))

    implementation(libs.coroutines.core)
}
