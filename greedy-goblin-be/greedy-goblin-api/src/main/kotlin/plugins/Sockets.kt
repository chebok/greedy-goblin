package io.greedygoblin.plugins

import io.greedy.goblin.api.v1.apiV1Serializer
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.websocket.*
import kotlin.time.Duration.Companion.seconds

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = 15.seconds
        timeout = 15.seconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
        contentConverter =
            KotlinxWebsocketSerializationConverter(
                apiV1Serializer,
            )
    }
}
