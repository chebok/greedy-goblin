package io.greedy.goblin.repo.inmemory.entity

data class SceneEntity(
    val description: String,
    val image: String,
    val actions: List<Action>,
) {
    data class Action(
        val id: String,
        val title: String,
    )
}
