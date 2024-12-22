package io.greedy.goblin.biz.stubs

import io.greedy.goblin.biz.GameLogicProcessor
import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.*
import io.greedy.goblin.common.stubs.AppStubs
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GameStartStubTest {
    private val processor = GameLogicProcessor()
    val gameId = GameId("game-started-123")

    @Test
    fun start() =
        runTest {
            val ctx =
                GameContext(
                    command = GameCommand.START,
                    commandState = CommandState.NONE,
                    workMode = AppWorkMode.STUB,
                    stubCase = AppStubs.SUCCESS,
                )
            processor.exec(ctx)
            assertEquals(gameId, ctx.gameId)
            assertEquals(GameState.ACTIVE, ctx.gameState)
            assertEquals(CommandState.FINISH, ctx.commandState)
        }
}
