package io.greedy.goblin.biz.stubs

import io.greedy.goblin.biz.GameLogicProcessor
import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.*
import io.greedy.goblin.common.stubs.AppStubs
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetSceneStubTest {
    private val processor = GameLogicProcessor()
    val gameId = GameId("game-started-123")
    val sceneId = SceneId("scene-001")

    @Test
    fun getScene() =
        runTest {
            val ctx =
                GameContext(
                    command = GameCommand.GET_SCENE,
                    commandState = CommandState.NONE,
                    gameId = gameId,
                    workMode = AppWorkMode.STUB,
                    stubCase = AppStubs.SUCCESS,
                )
            processor.exec(ctx)
            assertEquals(sceneId, ctx.gameScene.sceneId)
            assertEquals(CommandState.FINISH, ctx.commandState)
        }

    @Test
    fun internalServerError() =
        runTest {
            val ctx =
                GameContext(
                    command = GameCommand.GET_SCENE,
                    commandState = CommandState.NONE,
                    gameId = gameId,
                    workMode = AppWorkMode.STUB,
                    stubCase = AppStubs.SERVER_ERROR,
                )
            processor.exec(ctx)
            assertEquals("internal", ctx.errors.firstOrNull()?.group)
            assertEquals(CommandState.FAIL, ctx.commandState)
        }
}
