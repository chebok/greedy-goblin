package io.greedy.goblin.common.repo.player

import io.greedy.goblin.common.models.ActionId
import io.greedy.goblin.common.models.GameId
import io.greedy.goblin.common.models.PlayerId
import io.greedy.goblin.common.models.SceneId

data class PlayerActionDbRequest(
    val gameId: GameId,
    val playerId: PlayerId,
    val actionId: ActionId,
    val sceneId: SceneId,
)
