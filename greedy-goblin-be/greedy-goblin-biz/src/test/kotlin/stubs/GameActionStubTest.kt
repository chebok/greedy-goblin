package io.greedy.goblin.biz.stubs

import io.greedy.goblin.biz.GameLogicProcessor
import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.*
import io.greedy.goblin.common.stubs.AppStubs
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GameActionStubTest {
    private val processor = GameLogicProcessor()
    val gameId = GameId("game-started-123")
    val actionId = ActionId("action-000")

    @Test
    fun getScene() =
        runTest {
            val ctx =
                GameContext(
                    command = GameCommand.GET_SCENE,
                    commandState = CommandState.NONE,
                    gameId = gameId,
                    playerAction = actionId,
                    workMode = AppWorkMode.STUB,
                    stubCase = AppStubs.SUCCESS,
                )
            processor.exec(ctx)
            assertEquals(CommandState.FINISH, ctx.commandState)
        }

    @Test
    fun badRequestError() =
        runTest {
            val ctx =
                GameContext(
                    command = GameCommand.ACTION,
                    commandState = CommandState.NONE,
                    gameId = gameId,
                    playerAction = actionId,
                    workMode = AppWorkMode.STUB,
                    stubCase = AppStubs.BAD_REQUEST,
                )
            processor.exec(ctx)
            assertEquals("validation", ctx.errors.firstOrNull()?.group)
            assertEquals(CommandState.FAIL, ctx.commandState)
        }
}
