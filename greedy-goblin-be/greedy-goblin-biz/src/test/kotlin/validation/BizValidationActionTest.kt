package validation

import io.greedy.goblin.biz.validation.BaseBizValidationTest
import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.*
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizValidationActionTest : BaseBizValidationTest() {
    override val command = GameCommand.ACTION

    @Test
    fun unavailableAction() =
        runTest {
            val ctx =
                GameContext(
                    command = command,
                    commandState = CommandState.NONE,
                    workMode = AppWorkMode.TEST,
                    playerAction = ActionId("forbidden_action"),
                    gameId = GameId("game-123"),
                )
            processor.exec(ctx)
            assertNotEquals(0, ctx.errors.size)
            assertEquals(CommandState.FAIL, ctx.commandState)
        }

//    @Test
//    fun availableAction() =
//        runTest {
//            val ctx =
//                GameContext(
//                    command = command,
//                    commandState = CommandState.NONE,
//                    workMode = AppWorkMode.TEST,
//                    playerAction = ActionId("action-001"),
//                    gameId = GameId("game-123"),
//                )
//            processor.exec(ctx)
//            assertEquals(0, ctx.errors.size)
//            assertNotEquals(CommandState.FAIL, ctx.commandState)
//        }
}
