package io.greedy.goblin.api.plugins

import io.greedy.goblin.api.AppSettings
import io.greedy.goblin.api.features.game.gameModule
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureKoin(appSettings: AppSettings) {
    install(Koin) {
        modules(
            module {
                single { appSettings }
            },
            gameModule,
        )
    }
}
