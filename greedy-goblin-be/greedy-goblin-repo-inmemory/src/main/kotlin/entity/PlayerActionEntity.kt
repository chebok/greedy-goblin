package io.greedy.goblin.repo.inmemory.entity

data class PlayerActionEntity(
    val gameId: String,
    val playerId: String,
    val actionId: String,
    val sceneId: String,
)
