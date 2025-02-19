package io.greedy.goblin.common.repo.game

import io.greedy.goblin.common.models.GameError
import io.greedy.goblin.common.models.GameId
import io.greedy.goblin.common.repo.util.ERROR_GROUP_REPO

fun errorGameNotFound(gameId: GameId) =
    listOf(
        GameError(
            code = "$ERROR_GROUP_REPO-game-not-found",
            group = ERROR_GROUP_REPO,
            message = "Game $gameId not found ",
        ),
    )
