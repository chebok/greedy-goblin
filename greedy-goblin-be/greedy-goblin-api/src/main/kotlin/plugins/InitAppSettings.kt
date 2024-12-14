package io.greedy.goblin.api.plugins

import io.greedy.goblin.api.AppSettings
import io.greedy.goblin.biz.GameLogicProcessor
import io.greedy.goblin.common.CorSettings
import io.greedy.goblin.libs.logging.common.MpLoggerProvider
import io.greedy.goblin.libs.logging.jvm.mpLoggerLogback
import io.ktor.server.application.*

fun Application.initAppSettings(): AppSettings {
    val corSettings =
        CorSettings(
            loggerProvider = MpLoggerProvider { mpLoggerLogback(it) },
        )
    return AppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = GameLogicProcessor(corSettings),
    )
}
