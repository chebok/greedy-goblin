package io.greedy.goblin.api.stub

import io.greedy.goblin.api.AppSettings
import io.greedy.goblin.api.module
import io.greedy.goblin.api.v1.apiV1Serializer
import io.greedy.goblin.api.v1.models.GameActionRequest
import io.greedy.goblin.api.v1.models.GameActionResponse
import io.greedy.goblin.api.v1.models.GameSceneResponse
import io.greedy.goblin.common.CorSettings
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertTrue

class StubWsTest {
    @Test
    fun connectAndAction() =
        testApplication {
            application {
                module(AppSettings(corSettings = CorSettings()))
            }

            val client =
                createClient {
                    install(WebSockets) {
                        contentConverter = KotlinxWebsocketSerializationConverter(apiV1Serializer)
                    }
                }

            val gameId = "123"

            client.webSocket("/ws/game/$gameId") {
                val connectResponse = receiveDeserialized<GameSceneResponse>()
                assertTrue(connectResponse.success ?: false)

                val actionRequest =
                    GameActionRequest(
                        gameId = gameId,
                        actionId = "move-forward",
                    )
                sendSerialized(actionRequest)

                val actionResponse = receiveDeserialized<GameActionResponse>()
                assertTrue(actionResponse.success ?: false)
            }
        }
}
