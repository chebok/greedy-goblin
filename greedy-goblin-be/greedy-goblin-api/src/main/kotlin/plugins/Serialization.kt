package io.greedygoblin.plugins

import io.greedy.goblin.api.v1.apiV1Serializer
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(
            apiV1Serializer,
        )
    }
}
