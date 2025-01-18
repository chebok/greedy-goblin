package io.greedy.goblin.biz.logic

import io.greedy.goblin.common.CorSettings
import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.helpers.fail
import io.greedy.goblin.common.models.CommandState
import io.greedy.goblin.libs.cor.ICorChainDsl
import io.greedy.goblin.libs.cor.worker

fun ICorChainDsl<GameContext>.createGameProcessing(
    title: String,
    corSettings: CorSettings,
) = worker {
    this.title = title
    on { commandState == CommandState.RUN }
    handle {
        val gameRepo = corSettings.gameRepo

        gameRepo
            .createGame()
            .onRight { gameId = it }
            .mapLeft { fail(it) }
    }
}
