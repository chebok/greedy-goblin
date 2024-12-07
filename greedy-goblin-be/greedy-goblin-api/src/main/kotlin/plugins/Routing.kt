package io.greedy.goblin.api.plugins

import io.greedy.goblin.api.AppSettings
import io.greedy.goblin.api.features.game.configureGameRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val API_URL = "/api"

fun Application.configureRouting(appSettings: AppSettings) {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        route(API_URL) {
            route("v1") {
                configureGameRoutes(appSettings)
            }
        }
    }
}
