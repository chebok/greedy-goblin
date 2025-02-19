package io.greedy.goblin.api.features.game

import io.greedy.goblin.api.AppSettings
import io.greedy.goblin.api.common.controllerHelper
import io.greedy.goblin.api.common.messaging.SceneUpdate
import io.greedy.goblin.api.features.game.rooms.GameRoom
import io.greedy.goblin.api.v1.mappers.fromTransport
import io.greedy.goblin.api.v1.mappers.toTransport
import io.greedy.goblin.api.v1.models.*
import io.ktor.server.websocket.*
import kotlin.reflect.KClass

class GameController(
    private val appSettings: AppSettings,
) {
    private val rooms = mutableMapOf<String, GameRoom>()

    suspend fun startGame(request: GameStartRequest): GameStartResponse =
        process(
            appSettings = appSettings,
            request = request,
            clazz = this::class,
            logId = "start-game",
        )

    suspend fun handlePlayerAction(
        playerId: String,
        request: GameActionRequest,
    ) {
        val gameId = request.gameId
        val action = request.actionId ?: ""
        // Бизнес логика
        val response = performAction(request)
        rooms[gameId]?.let { room ->
            room.sendToPlayer(
                playerId = playerId,
                message = response,
            )
            room.processAction(
                playerId = playerId,
                action = action,
            )
        }
    }

    suspend fun addPlayerToRoom(
        gameId: String,
        playerId: String,
        session: WebSocketServerSession,
    ) {
        // Бизнес логика
        val room = rooms.getOrPut(gameId) { GameRoom(gameId) }
        room.addPlayer(playerId, session)
    }

    fun removePlayerFromRoom(
        gameId: String,
        playerId: String,
    ) {
        rooms[gameId]?.removePlayer(playerId)
        if (rooms[gameId]?.isEmpty() == true) {
            rooms.remove(gameId)
        }
    }

    suspend fun updateGameSceneForAllPlayers(sceneUpdate: SceneUpdate) {
        val gameId = sceneUpdate.gameId
        val gameSceneResponse =
            getScene(
                GameSceneRequest(
                    gameId = gameId,
                ),
            )
        rooms[gameId]?.updateSceneToPlayers(gameSceneResponse)
    }

    private suspend fun getScene(request: GameSceneRequest): GameSceneResponse =
        process(
            appSettings = appSettings,
            request = request,
            clazz = this::class,
            logId = "get-scene",
        )

    private suspend fun performAction(request: GameActionRequest): GameActionResponse =
        process(
            appSettings = appSettings,
            request = request,
            clazz = this::class,
            logId = "game-action",
        )

    companion object {
        suspend inline fun <reified Q : Any, reified R> process(
            appSettings: AppSettings,
            request: Q,
            clazz: KClass<*>,
            logId: String,
        ): R =
            appSettings.controllerHelper(
                {
                    fromTransport(request)
                },
                { toTransport() as R },
                clazz,
                logId,
            )
    }
}
