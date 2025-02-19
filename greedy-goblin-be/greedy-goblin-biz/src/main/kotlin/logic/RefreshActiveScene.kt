package io.greedy.goblin.biz.logic

import arrow.core.flatMap
import arrow.core.right
import io.greedy.goblin.common.CorSettings
import io.greedy.goblin.common.GameContext
import io.greedy.goblin.common.helpers.fail
import io.greedy.goblin.common.models.*
import io.greedy.goblin.common.repo.game.SetSceneDbRequest
import io.greedy.goblin.common.repo.scene.CreateSceneDbRequest
import io.greedy.goblin.libs.cor.ICorChainDsl
import io.greedy.goblin.libs.cor.worker

fun ICorChainDsl<GameContext>.refreshActiveScene(
    title: String,
    corSettings: CorSettings,
) = worker {
    this.title = title
    on { commandState == CommandState.RUN }
    handle {
        val sceneRepo = corSettings.sceneRepo
        val gameRepo = corSettings.gameRepo

        // Генерация новой сцены каким-то образом, ai
        val createSceneDbRequest =
            CreateSceneDbRequest(
                description = "You see a path splitting in two.",
                image = "url",
                actions =
                    listOf(
                        Action(ActionId("action-001"), "Take the left path"),
                        Action(ActionId("action-002"), "Take the right path"),
                    ),
            )

        if ((gameScene.sceneId == SceneId.NONE) || players.all { it.state == PlayerState.ACTION_MADE }) {
            sceneRepo
                .save(createSceneDbRequest)
                .flatMap { savedSceneId ->
                    gameScene =
                        GameScene(
                            sceneId = savedSceneId,
                            description = createSceneDbRequest.description,
                            image = createSceneDbRequest.image,
                            actions = createSceneDbRequest.actions,
                        )
                    savedSceneId.right()
                }.flatMap { savedSceneId ->
                    gameRepo.setScene(SetSceneDbRequest(gameId = gameId, sceneId = savedSceneId))
                }.flatMap {
                    gameRepo.refreshState(gameId)
                }.mapLeft { fail(it) }
        }
    }
}
