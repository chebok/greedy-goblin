package io.greedy.goblin.common.repo.util

import io.greedy.goblin.common.models.GameError

const val ERROR_GROUP_REPO = "repo"

fun errorNotImplemented(methodName: String) =
    listOf(
        GameError(
            code = "$ERROR_GROUP_REPO-not-implemented",
            group = ERROR_GROUP_REPO,
            message = "Method $methodName is not implemented in this repository",
        ),
    )
