package io.greedy.goblin.common.models

import io.greedy.goblin.libs.logging.common.LogLevel

data class GameError(
    val code: String = "",
    val message: String = "",
    val group: String = "",
    val level: LogLevel = LogLevel.ERROR,
    val exception: Throwable? = null,
)
