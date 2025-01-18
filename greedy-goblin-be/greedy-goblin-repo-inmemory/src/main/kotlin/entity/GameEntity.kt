package io.greedy.goblin.repo.inmemory.entity

import io.greedy.goblin.common.models.*

data class GameEntity(
    val sceneId: String? = null,
    val state: GameState,
    val players: List<Player>,
) {
    data class Player(
        val id: String,
        val state: PlayerState,
    )
}
