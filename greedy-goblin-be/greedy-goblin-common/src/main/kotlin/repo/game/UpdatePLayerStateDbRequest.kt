package io.greedy.goblin.common.repo.game

import io.greedy.goblin.common.models.GameId
import io.greedy.goblin.common.models.PlayerId

data class UpdatePLayerStateDbRequest(
    val gameId: GameId,
    val playerId: PlayerId,
)
