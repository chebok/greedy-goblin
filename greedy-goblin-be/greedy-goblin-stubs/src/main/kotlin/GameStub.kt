import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.*

object GameStub {
    fun processContext(ctx: GameContext) {
        when (ctx.command) {
            GameCommand.START -> startGame(ctx)
            GameCommand.GET_SCENE -> getScene(ctx)
            GameCommand.ACTION -> performAction(ctx)
            GameCommand.END -> endGame(ctx)
            GameCommand.NONE -> invalidRequest(ctx)
            GameCommand.CONNECT -> getScene(ctx)
            GameCommand.DISCONNECT -> disconnect(ctx)
        }
    }

    private fun disconnect(ctx: GameContext) {
    }

    private fun startGame(ctx: GameContext) {
        ctx.gameId = GameId("game-started-123")
        ctx.gameState = GameState.ACTIVE
    }

    private fun getScene(ctx: GameContext) {
        ctx.gameScene =
            GameScene(
                sceneId = SceneId("scene-001"),
                description = "You see a path splitting in two.",
                image = "",
                actions =
                    listOf(
                        Action(ActionId("action-001"), "Take the left path"),
                        Action(ActionId("action-002"), "Take the right path"),
                    ),
            )
        ctx.commandState = CommandState.FINISH
    }

    private fun performAction(ctx: GameContext) {
        ctx.commandState = CommandState.FINISH
    }

    private fun endGame(ctx: GameContext) {
        ctx.gameState = GameState.END
    }

    private fun invalidRequest(ctx: GameContext) {
        ctx.errors.add(GameError("Unknown command", "command"))
    }
}
