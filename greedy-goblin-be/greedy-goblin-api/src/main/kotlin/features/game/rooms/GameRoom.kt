package io.greedy.goblin.api.features.game.rooms

import io.greedy.goblin.api.common.messaging.InMemoryEventBroker
import io.greedy.goblin.api.common.messaging.SceneUpdate
import io.greedy.goblin.api.v1.models.GameSceneResponse
import io.ktor.server.websocket.*

class GameRoom(
    private val gameId: String,
) {
    private val inMemoryEventBroker = InMemoryEventBroker
    private val players = mutableMapOf<String, WebSocketServerSession>()
    private val actions = mutableMapOf<String, String>()

    suspend fun addPlayer(
        playerId: String,
        session: WebSocketServerSession,
    ) {
        players[playerId] = session
        notifySceneGeneration()
    }

    suspend fun sendToPlayer(
        playerId: String,
        message: Any,
    ) {
        val session: WebSocketServerSession? = players[playerId]
        session?.sendSerialized(message)
    }

    fun removePlayer(playerId: String) {
        players.remove(playerId)
    }

    fun isEmpty(): Boolean = players.isEmpty()

    suspend fun processAction(
        playerId: String,
        action: String,
    ) {
        actions[playerId] = action

        if (actions.size == players.size) {
            notifySceneGeneration()
            actions.clear()
        }
    }

    private suspend fun notifySceneGeneration() {
        inMemoryEventBroker.emitSceneUpdate(
            SceneUpdate(
                gameId = gameId,
            ),
        )
    }

    suspend fun updateSceneToPlayers(sceneResponse: GameSceneResponse) {
        players.values.forEach { session ->
            session.sendSerialized(sceneResponse)
        }
    }
}
