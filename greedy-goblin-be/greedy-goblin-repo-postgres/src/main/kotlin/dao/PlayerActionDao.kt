package io.greedy.goblin.repo.postgres.dao

import io.greedy.goblin.repo.postgres.entity.PlayerActionEntity
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object PlayerActionTable : IntIdTable("player_actions") {
    val gameId = uuid("game_id")
    val playerId = uuid("player_id")
    val actionId = varchar("action_id", 50)
    val sceneId = uuid("scene_id")
}

class PlayerActionDAO(
    id: EntityID<Int>,
) : IntEntity(id) {
    companion object : IntEntityClass<PlayerActionDAO>(PlayerActionTable)

    var gameId by PlayerActionTable.gameId
    var playerId by PlayerActionTable.playerId
    var actionId by PlayerActionTable.actionId
    var sceneId by PlayerActionTable.sceneId

    fun toEntity() =
        PlayerActionEntity(
            gameId = gameId.toString(),
            playerId = playerId.toString(),
            actionId = actionId,
            sceneId = sceneId.toString(),
        )
}
