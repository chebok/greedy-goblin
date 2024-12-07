package io.greedy.goblin.common.helpers

import io.greedy.goblin.common.models.GameError

fun Throwable.asGameError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = GameError(
    code = code,
    group = group,
    message = message,
    exception = this,
)
