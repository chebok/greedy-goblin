package io.greedy.goblin.biz.stubs

import io.greedy.goblin.common.CorSettings
import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.CommandState
import io.greedy.goblin.common.models.GameId
import io.greedy.goblin.common.models.GameState
import io.greedy.goblin.common.stubs.AppStubs
import io.greedy.goblin.libs.cor.ICorChainDsl
import io.greedy.goblin.libs.cor.worker
import io.greedy.goblin.libs.logging.common.LogLevel

fun ICorChainDsl<GameContext>.gameStartSuccess(
    title: String,
    corSettings: CorSettings,
) = worker {
    this.title = title
    this.description =
        """
        Кейс успешного создания игры
        """.trimIndent()
    on { stubCase == AppStubs.SUCCESS && commandState == CommandState.RUN }
    val logger = corSettings.loggerProvider.logger("gameStartSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            gameId = GameId("game-started-123")
            gameState = GameState.ACTIVE
            commandState = CommandState.FINISH
        }
    }
}
