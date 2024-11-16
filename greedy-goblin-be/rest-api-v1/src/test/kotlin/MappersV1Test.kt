package io.goblingamble.api.v1

import GameContext
import io.goblingamble.api.v1.mappers.fromTransport
import io.goblingamble.api.v1.mappers.toTransport
import io.goblingamble.api.v1.models.*
import models.*
import stubs.AppStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class MappersV1Test {
    @Test
    fun fromTransport() {
        val req =
            GameActionRequest(
                debug =
                    GameRequestDebug(
                        mode = GameRequestDebugMode.STUB,
                        stub = GameRequestDebugStubs.SUCCESS,
                    ),
                gameId = "game-123",
                actionId = "action-456",
            )

        val context = GameContext()
        context.fromTransport(req)

        assertEquals(AppStubs.SUCCESS, context.stubCase)
        assertEquals(AppWorkMode.STUB, context.workMode)
        assertEquals(GameId("game-123"), context.gameId)
        assertEquals(ActionId("action-456"), context.playerAction)
    }

    @Test
    fun toTransportGameScene() {
        val context =
            GameContext(
                command = GameCommand.GET_SCENE,
                commandState = CommandState.FINISH,
                gameId = GameId("game-123"),
                gameScene =
                    GameScene(
                        sceneId = SceneId("scene-456"),
                        description = "You are standing in a dark forest surrounded by tall trees.",
                        image = "https://example.com/forest.jpg",
                        actions =
                            listOf(
                                Action(ActionId("1"), "Move Forward"),
                                Action(ActionId("2"), "Look Around"),
                            ),
                    ),
                errors = mutableListOf(),
            )

        val response = context.toTransport() as GameSceneResponse

        assertEquals(true, response.success)
        assertEquals("Scene retrieved successfully", response.message)
        assertEquals("scene-456", response.scene?.sceneId)
        assertEquals(
            "You are standing in a dark forest surrounded by tall trees.",
            response.scene?.sceneTitle,
        )
        assertEquals("https://example.com/forest.jpg", response.scene?.image)
        assertEquals(2, response.scene?.playerActions?.size)

        val firstAction = response.scene?.playerActions?.get(0)
        assertEquals("1", firstAction?.id)
        assertEquals("Move Forward", firstAction?.title)

        val secondAction = response.scene?.playerActions?.get(1)
        assertEquals("2", secondAction?.id)
        assertEquals("Look Around", secondAction?.title)
    }
}
