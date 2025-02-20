package io.greedy.goblin.api.features.game

import io.greedy.goblin.api.v1.models.GameStartRequest
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureGameRoutes(gameController: GameController) {
    route("/") {
        post("game/start") {
            val request = call.receive<GameStartRequest>()
            val response = gameController.startGame(request)
            call.respond(response)
        }
    }
}
