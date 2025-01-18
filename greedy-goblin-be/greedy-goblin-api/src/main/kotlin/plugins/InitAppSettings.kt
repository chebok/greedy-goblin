package io.greedy.goblin.api.plugins

import io.greedy.goblin.api.AppSettings
import io.greedy.goblin.biz.GameLogicProcessor
import io.greedy.goblin.common.CorSettings
import io.greedy.goblin.libs.logging.common.MpLoggerProvider
import io.greedy.goblin.libs.logging.jvm.mpLoggerLogback
import io.greedy.goblin.repo.inmemory.GameRepoInMemory
import io.greedy.goblin.repo.inmemory.PlayerActionRepoInMemory
import io.greedy.goblin.repo.inmemory.SceneRepoInMemory
import io.ktor.server.application.*

fun Application.initAppSettings(): AppSettings {
    val corSettings =
        CorSettings(
            loggerProvider = MpLoggerProvider { mpLoggerLogback(it) },
            gameRepo = GameRepoInMemory(),
            sceneRepo = SceneRepoInMemory(),
            playerActionRepo = PlayerActionRepoInMemory(),
        )
    return AppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = GameLogicProcessor(corSettings),
    )
}
