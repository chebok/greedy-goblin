package io.greedy.goblin.api.inmemory

import io.greedy.goblin.api.module
import io.greedy.goblin.api.v1.apiV1Serializer
import io.greedy.goblin.api.v1.models.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertTrue

class InMemoryWsTest {
    @Test
    fun connectAndAction() =
        testApplication {
            application {
                module(config = ApplicationConfig("application-test.yaml"))
            }

            val client =
                createClient {
                    install(ContentNegotiation) {
                        json(apiV1Serializer)
                    }
                    install(WebSockets) {
                        contentConverter = KotlinxWebsocketSerializationConverter(apiV1Serializer)
                    }
                }

            val gameId =
                client
                    .post("/api/v1/game/start") {
                        contentType(ContentType.Application.Json)
                        setBody(
                            GameStartRequest(
                                mode = GameStartRequest.Mode.SINGLE,
                                debug =
                                    GameRequestDebug(
                                        mode = GameRequestDebugMode.TEST,
                                    ),
                            ),
                        )
                    }.body<GameStartResponse>()
                    .gameId

            client.webSocket("/ws/game/$gameId") {
                val connectResponse = receiveDeserialized<GameSceneResponse>()
                assertTrue(connectResponse.success ?: false)

                val actionRequest =
                    GameActionRequest(
                        gameId = gameId,
                        actionId = "action-001",
                        debug =
                            GameRequestDebug(
                                mode = GameRequestDebugMode.TEST,
                            ),
                    )
                sendSerialized(actionRequest)

                val actionResponse = receiveDeserialized<GameActionResponse>()
                assertTrue(actionResponse.success ?: false)
            }
        }
}
