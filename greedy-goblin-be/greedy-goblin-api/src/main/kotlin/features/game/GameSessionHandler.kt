package io.greedy.goblin.api.features.game

import io.greedy.goblin.api.v1.apiV1Serializer
import io.greedy.goblin.api.v1.models.GameActionRequest
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.SerializationException

suspend fun WebSocketServerSession.handleGameSession(gameController: GameController) =
    with(this) {
        val gameId = call.parameters["gameId"] ?: ""
        // Get playerId from token
        val playerId = "player-$gameId"
        // Only through GameController
        gameController.addPlayerToRoom(
            gameId = gameId,
            playerId = playerId,
            session = this,
        )

        // Handle flow
        incoming
            .receiveAsFlow()
            .onCompletion {
                gameController.removePlayerFromRoom(
                    gameId = gameId,
                    playerId = playerId,
                )
            }.filterIsInstance<Frame.Text>()
            .collect { message ->
                try {
                    val request: GameActionRequest = apiV1Serializer.decodeFromString(message.readText())
                    gameController.handlePlayerAction(request)
                } catch (e: SerializationException) {
                    println("Wrong data: $message")
                }
            }
    }
