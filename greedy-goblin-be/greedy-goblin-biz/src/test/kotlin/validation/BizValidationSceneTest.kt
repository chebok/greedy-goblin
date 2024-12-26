package validation

import io.greedy.goblin.biz.validation.BaseBizValidationTest
import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.*
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizValidationSceneTest : BaseBizValidationTest() {
    override val command = GameCommand.GET_SCENE
    val correctGameId = GameId("game-started-123")
    val inactiveGameId = GameId("game-finished-123")

    @Test
    fun unavailableGame() =
        runTest {
            val ctx =
                GameContext(
                    command = command,
                    commandState = CommandState.NONE,
                    workMode = AppWorkMode.TEST,
                    gameId = inactiveGameId,
                )
            processor.exec(ctx)
            assertNotEquals(0, ctx.errors.size)
            assertEquals(CommandState.FAIL, ctx.commandState)
        }

    @Test
    fun activeGame() =
        runTest {
            val ctx =
                GameContext(
                    command = command,
                    commandState = CommandState.NONE,
                    workMode = AppWorkMode.TEST,
                    gameId = correctGameId,
                )
            processor.exec(ctx)
            assertEquals(0, ctx.errors.size)
            assertNotEquals(CommandState.FAIL, ctx.commandState)
        }
}
