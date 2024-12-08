package io.greedy.goblin.api.plugins

import io.greedy.goblin.api.common.messaging.InMemoryEventBroker
import io.greedy.goblin.api.features.game.GameController
import io.ktor.server.application.*
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject

fun Application.initSceneUpdates() {
    val gameController: GameController by inject()

    launch {
        InMemoryEventBroker.subscribeSceneUpdate().collect {
            gameController.updateGameSceneForAllPlayers(it)
        }
    }
}
