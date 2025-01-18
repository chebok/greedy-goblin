package io.greedy.goblin.common.repo.player

import io.greedy.goblin.common.models.GameError

const val ERROR_GROUP_REPO = "repo"

fun errorDuplicateAction(request: PlayerActionDbRequest) =
    listOf(
        GameError(
            code = "$ERROR_GROUP_REPO-duplicate-action",
            group = ERROR_GROUP_REPO,
            message =
                "Action with ID: ${request.actionId} already exists " +
                    "for player ${request.playerId} in game ${request.gameId} " +
                    "and scene ${request.sceneId}",
        ),
    )
