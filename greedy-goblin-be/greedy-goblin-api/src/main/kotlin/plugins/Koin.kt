package io.greedy.goblin.api.plugins

import io.greedy.goblin.api.AppSettings
import io.greedy.goblin.api.db.databaseModule
import io.greedy.goblin.api.features.game.gameModule
import io.greedy.goblin.biz.GameLogicProcessor
import io.greedy.goblin.common.CorSettings
import io.greedy.goblin.common.repo.game.IGameRepo
import io.greedy.goblin.common.repo.player.IPlayerActionRepo
import io.greedy.goblin.common.repo.scene.ISceneRepo
import io.greedy.goblin.libs.logging.common.MpLoggerProvider
import io.greedy.goblin.libs.logging.jvm.mpLoggerLogback
import io.ktor.server.application.*
import io.ktor.server.config.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureKoin(config: ApplicationConfig) {
    install(Koin) {
        modules(
            module { single { config } },
            databaseModule(config),
            module {
                single {
                    val corSettings =
                        CorSettings(
                            loggerProvider = MpLoggerProvider { mpLoggerLogback(it) },
                            gameRepo = get<IGameRepo>(),
                            sceneRepo = get<ISceneRepo>(),
                            playerActionRepo = get<IPlayerActionRepo>(),
                        )
                    AppSettings(
                        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
                        corSettings = corSettings,
                        processor = GameLogicProcessor(corSettings),
                    )
                }
            },
            gameModule,
        )
    }
}
