package io.greedy.goblin.common.repo.game

import arrow.core.Either
import io.greedy.goblin.common.models.*

typealias IGameDbResponse = Either<List<GameError>, GameData>

data class GameData(
    val id: GameId,
    val sceneId: SceneId,
    val state: GameState,
    val players: List<Player>,
)
