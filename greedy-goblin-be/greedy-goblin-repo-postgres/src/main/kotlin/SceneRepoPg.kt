package io.greedy.goblin.repo.postgres

import arrow.core.Either
import arrow.core.right
import io.greedy.goblin.common.models.*
import io.greedy.goblin.common.repo.scene.CreateSceneDbRequest
import io.greedy.goblin.common.repo.scene.ISceneRepo
import io.greedy.goblin.common.repo.scene.errorSceneNotFound
import io.greedy.goblin.common.repo.util.safeDbCall
import io.greedy.goblin.common.repo.util.toEither
import io.greedy.goblin.repo.postgres.dao.SceneTable
import io.greedy.goblin.repo.postgres.entity.SceneEntity
import io.greedy.goblin.repo.postgres.util.suspendTransaction
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import java.util.UUID
import javax.sql.DataSource

class SceneRepoPg(
    private val dataSource: DataSource,
) : ISceneRepo {
    override suspend fun save(request: CreateSceneDbRequest): Either<List<GameError>, SceneId> =
        safeDbCall {
            dataSource.connection.use { connection ->
                val sceneId = UUID.randomUUID()

                val sceneEntity =
                    SceneEntity(
                        description = request.description,
                        image = request.image,
                        actions = request.actions.map { SceneEntity.Action(it.id.asString(), it.title) },
                    )
                val serializedSceneEntity = Json.encodeToString(sceneEntity)

                val query =
                    """
                    INSERT INTO scenes (scene_id, data)
                    VALUES (?, ?::jsonb)
                    """.trimIndent()

                connection.prepareStatement(query).use { preparedStatement ->
                    preparedStatement.setObject(1, sceneId)
                    preparedStatement.setString(2, serializedSceneEntity)

                    preparedStatement.executeUpdate()
                    connection.commit()
                }
                SceneId(sceneId.toString()).right()
            }
        }

    override suspend fun getSceneData(sceneId: SceneId): Either<List<GameError>, GameScene> =
        safeDbCall {
            suspendTransaction {
                SceneTable
                    .selectAll()
                    .where { SceneTable.sceneId eq UUID.fromString(sceneId.asString()) }
                    .mapNotNull { row ->
                        row[SceneTable.data].let { json ->
                            val sceneEntity = Json.decodeFromString<SceneEntity>(json) // Десериализация из JSON
                            GameScene(
                                sceneId = sceneId,
                                description = sceneEntity.description,
                                image = sceneEntity.image,
                                actions = sceneEntity.actions.map { Action(ActionId(it.id), it.title) },
                            )
                        }
                    }.singleOrNull()
                    .toEither { errorSceneNotFound(sceneId) }
            }
        }
}
