package io.greedy.goblin.repo.postgres.entity

data class PlayerActionEntity(
    val gameId: String,
    val playerId: String,
    val actionId: String,
    val sceneId: String,
)
