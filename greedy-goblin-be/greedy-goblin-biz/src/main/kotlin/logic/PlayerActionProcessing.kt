package io.greedy.goblin.biz.logic

import arrow.core.flatMap
import io.greedy.goblin.common.CorSettings
import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.helpers.fail
import io.greedy.goblin.common.models.CommandState
import io.greedy.goblin.common.repo.game.UpdatePLayerStateDbRequest
import io.greedy.goblin.common.repo.player.PlayerActionDbRequest
import io.greedy.goblin.libs.cor.ICorChainDsl
import io.greedy.goblin.libs.cor.worker

fun ICorChainDsl<GameContext>.playerActionProcessing(
    title: String,
    corSettings: CorSettings,
) = worker {
    this.title = title
    on { commandState == CommandState.RUN }
    handle {
        val playerActionRepo = corSettings.playerActionRepo
        val gameRepo = corSettings.gameRepo

        // В транзакции надо делать
        playerActionRepo
            .save(
                PlayerActionDbRequest(
                    gameId = gameId,
                    playerId = playerId,
                    sceneId = gameScene.sceneId,
                    actionId = playerAction,
                ),
            ).flatMap {
                gameRepo.updatePlayerStateAfterAction(
                    UpdatePLayerStateDbRequest(
                        gameId = gameId,
                        playerId = playerId,
                    ),
                )
            }.mapLeft {
                fail(it)
            }
    }
}
