package io.greedy.goblin.repo.postgres.dao

import org.jetbrains.exposed.sql.Table

object SceneTable : Table("scenes") {
    val sceneId = uuid("scene_id")
    val data = text("data") // Храним всю структуру SceneEntity как jsonb
}

class SceneDao
