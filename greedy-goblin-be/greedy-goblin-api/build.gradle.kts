plugins {
    id("build-jvm")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ktor)
}

jib {
    from {
        image = "eclipse-temurin:21-jre" // Образ для Java 21 (JRE)
    }
    to {
        image = "greedy-goblin:latest"
    }
    container {
        mainClass = "io.greedy.goblin.api.ApplicationKt"
    }
}

dependencies {
    implementation("io.insert-koin:koin-ktor:3.5.0")
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.yaml)
    implementation(libs.ktor.server.negotiation)
    implementation(libs.ktor.server.headers.response)
    implementation(libs.ktor.server.headers.caching)
    implementation(libs.ktor.server.websocket)
//                // Для того, чтоб получать содержимое запроса более одного раза
//                В Application.main добавить `install(DoubleReceive)`
//                implementation("io.ktor:ktor-server-double-receive:${libs.versions.ktor.get()}")

    // DB
    implementation(projects.greedyGoblinRepoInmemory)
    implementation(projects.greedyGoblinRepoPostgres)
    implementation(libs.db.postgres)
    implementation(libs.db.hikari)
    implementation(libs.bundles.exposed)
    implementation("org.flywaydb:flyway-database-postgresql:11.2.0")

    implementation(project(":greedy-goblin-common"))
    implementation(project(":greedy-goblin-biz"))

    // v1 api
    implementation(project(":rest-api-v1"))

    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.serialization.json)

    // jackson
    implementation(libs.ktor.serialization.jackson)
    implementation(libs.ktor.server.calllogging)
    implementation(libs.ktor.server.headers.default)

    implementation(libs.logback)

    // logging
    implementation(project(":greedy-goblin-log"))
    implementation("io.greedy.goblin.libs:lib-logging-common")
    implementation("io.greedy.goblin.libs:lib-logging-kermit")
    implementation("io.greedy.goblin.libs:lib-logging-logback")

    // test
    implementation(kotlin("test"))
    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))
    implementation(kotlin("test-junit"))

    implementation(libs.ktor.server.test)
    implementation(libs.ktor.client.negotiation)
}
