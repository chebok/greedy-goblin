package test.action.v1

import fixture.client.Client
import io.goblingamble.api.v1.apiV1Serializer
import io.goblingamble.api.v1.models.GameStartRequest
import io.goblingamble.api.v1.models.GameStartResponse
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.serialization.encodeToString

suspend fun Client.startGame(mode: GameStartRequest.Mode = GameStartRequest.Mode.SINGLE): GameStartResponse =
    startGame(mode) {
        it.success shouldBe true
        it.gameId shouldNotBe null
        it.message shouldBe "Game started successfully"
        it
    }

suspend fun <T> Client.startGame(
    mode: GameStartRequest.Mode = GameStartRequest.Mode.SINGLE,
    block: (GameStartResponse) -> T,
): T =
    withClue("startGameV1: mode=$mode") {
        val request =
            GameStartRequest(
                mode = mode,
            )
        val serializedResponse =
            sendAndReceive(
                "v1",
                "game/start",
                apiV1Serializer.encodeToString(request),
            )

        val response = apiV1Serializer.decodeFromString<GameStartResponse>(serializedResponse)

        response.asClue(block)
    }
