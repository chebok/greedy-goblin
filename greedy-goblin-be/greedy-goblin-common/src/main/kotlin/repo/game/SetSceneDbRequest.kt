package io.greedy.goblin.common.repo.game

import io.greedy.goblin.common.models.GameId
import io.greedy.goblin.common.models.SceneId

data class SetSceneDbRequest(
    val gameId: GameId,
    val sceneId: SceneId,
)
