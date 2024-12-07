package io.greedy.goblin.api.stub

import io.greedy.goblin.api.AppSettings
import io.greedy.goblin.api.module
import io.greedy.goblin.api.v1.apiV1Serializer
import io.greedy.goblin.api.v1.models.*
import io.greedy.goblin.common.CorSettings
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class V1AdStubApiTest {
    @Test
    fun create() =
        v1TestApplication(
            path = "game/start",
            request =
                GameStartRequest(
                    mode = GameStartRequest.Mode.SINGLE,
                    debug =
                        GameRequestDebug(
                            mode = GameRequestDebugMode.STUB,
                            stub = GameRequestDebugStubs.SUCCESS,
                        ),
                ),
        ) { response ->
            val responseObj = response.body<GameStartResponse>()
            assertEquals(200, response.status.value)
            assertEquals("game-started-123", responseObj.gameId)
        }

    private fun v1TestApplication(
        path: String,
        request: Any,
        function: suspend (HttpResponse) -> Unit,
    ): Unit =
        testApplication {
            application { module(AppSettings(corSettings = CorSettings())) }
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
