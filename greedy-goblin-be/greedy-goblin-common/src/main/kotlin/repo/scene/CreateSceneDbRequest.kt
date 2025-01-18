package io.greedy.goblin.common.repo.scene

import io.greedy.goblin.common.models.Action

data class CreateSceneDbRequest(
    var description: String,
    var image: String,
    var actions: List<Action>,
)
