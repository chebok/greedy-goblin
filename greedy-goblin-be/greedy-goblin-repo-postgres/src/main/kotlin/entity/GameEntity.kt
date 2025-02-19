package io.greedy.goblin.repo.postgres.entity

import io.greedy.goblin.common.models.GameState
import kotlinx.serialization.Serializable

data class GameEntity(
    val gameId: String,
    val sceneId: String? = null,
    val state: GameState,
    val players: List<Player>,
) {
    @Serializable
    data class Player(
        val id: String,
        val state: PlayerStateDb,
    )
}
