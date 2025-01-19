package io.greedy.goblin.api.inmemory

import io.greedy.goblin.api.module
import io.greedy.goblin.api.v1.apiV1Serializer
import io.greedy.goblin.api.v1.models.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InMemoryApiTest {
    @Test
    fun create() =
        v1TestApplication(
            path = "game/start",
            request =
                GameStartRequest(
                    mode = GameStartRequest.Mode.SINGLE,
                    debug =
                        GameRequestDebug(
                            mode = GameRequestDebugMode.TEST,
                        ),
                ),
        ) { response ->
            val responseObj = response.body<GameStartResponse>()
            assertEquals(200, response.status.value)
            assertTrue(responseObj.gameId?.isNotEmpty() ?: false)
        }

    private fun v1TestApplication(
        path: String,
        request: Any,
        function: suspend (HttpResponse) -> Unit,
    ): Unit =
        testApplication {
            application {
                module(config = ApplicationConfig("application-test.yaml"))
            }
            val client =
                createClient {
                    install(ContentNegotiation) {
                        json(apiV1Serializer)
                    }
                }
            val response =
                client.post("/api/v1/$path") {
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
            function(response)
        }
}
