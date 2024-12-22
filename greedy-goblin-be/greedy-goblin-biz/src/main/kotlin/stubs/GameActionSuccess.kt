package io.greedy.goblin.biz.stubs

import io.greedy.goblin.common.CorSettings
import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.models.CommandState
import io.greedy.goblin.common.stubs.AppStubs
import io.greedy.goblin.libs.cor.ICorChainDsl
import io.greedy.goblin.libs.cor.worker
import io.greedy.goblin.libs.logging.common.LogLevel

fun ICorChainDsl<GameContext>.gameActionSuccess(
    title: String,
    corSettings: CorSettings,
) = worker {
    this.title = title
    this.description =
        """
        Кейс успешного создания игры
        """.trimIndent()
    on { stubCase == AppStubs.SUCCESS && commandState == CommandState.RUN }
    val logger = corSettings.loggerProvider.logger("gameActionSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            commandState = CommandState.FINISH
        }
    }
}
