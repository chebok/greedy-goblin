package io.greedy.goblin.common

import io.greedy.goblin.libs.logging.common.MpLoggerProvider

data class CorSettings(
    val loggerProvider: MpLoggerProvider = MpLoggerProvider(),
) {
    companion object {
        val NONE = CorSettings()
    }
}
