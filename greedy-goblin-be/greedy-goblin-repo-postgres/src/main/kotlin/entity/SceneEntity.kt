package io.greedy.goblin.repo.postgres.entity

import kotlinx.serialization.Serializable

@Serializable
data class SceneEntity(
    val description: String,
    val image: String,
    val actions: List<Action>,
) {
    @Serializable
    data class Action(
        val id: String,
        val title: String,
    )
}
