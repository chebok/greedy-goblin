package io.greedy.goblin.api.plugins

import io.greedy.goblin.api.features.game.GameController
import io.greedy.goblin.api.features.game.configureGameRoutes
import io.greedy.goblin.api.features.game.handleGameSession
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject

const val API_URL = "/api/v1"

fun Application.configureRouting() {
    val gameController: GameController by inject()
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        route(API_URL) {
            configureGameRoutes(gameController)
        }
        webSocket("/ws/game/{gameId}") {
            handleGameSession(gameController)
        }
    }
}
