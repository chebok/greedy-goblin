package io.greedy.goblin.common.repo.scene

import io.greedy.goblin.common.models.GameError
import io.greedy.goblin.common.models.SceneId
import io.greedy.goblin.common.repo.util.ERROR_GROUP_REPO

fun errorSceneNotFound(sceneId: SceneId) =
    listOf(
        GameError(
            code = "$ERROR_GROUP_REPO-scene-not-found",
            group = ERROR_GROUP_REPO,
            message = "Game $sceneId not found ",
        ),
    )
