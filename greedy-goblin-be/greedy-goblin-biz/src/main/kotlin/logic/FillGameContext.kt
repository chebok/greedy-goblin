package io.greedy.goblin.biz.logic

import arrow.core.flatMap
import arrow.core.right
import io.greedy.goblin.common.CorSettings
import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.helpers.fail
import io.greedy.goblin.common.models.CommandState
import io.greedy.goblin.common.models.SceneId
import io.greedy.goblin.libs.cor.ICorChainDsl
import io.greedy.goblin.libs.cor.worker

fun ICorChainDsl<GameContext>.fillGameContext(
    title: String,
    corSettings: CorSettings,
) = worker {
    this.title = title
    on { commandState == CommandState.RUN }
    handle {
        val gameRepo = corSettings.gameRepo
        val sceneRepo = corSettings.sceneRepo

        gameRepo
            .getGameData(gameId)
            .flatMap { gameData ->
                players = gameData.players
                gameState = gameData.state

                if (gameData.sceneId != SceneId.NONE) {
                    sceneRepo.getSceneData(gameData.sceneId).map { sceneData ->
                        // Наполняем контекст GameScene
                        gameScene = sceneData
                    }
                } else {
                    Unit.right()
                }
            }.mapLeft {
                fail(it)
            }
    }
}
