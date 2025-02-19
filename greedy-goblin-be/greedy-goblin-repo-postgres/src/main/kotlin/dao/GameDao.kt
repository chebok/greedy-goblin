package io.greedy.goblin.repo.postgres.dao

import org.jetbrains.exposed.sql.Table

object GameTable : Table("games") {
    val gameId = uuid("game_id")
    val sceneId = uuid("scene_id").nullable()
    val state = varchar("state", 50)
    val players = text("players")
}

class GameDao
